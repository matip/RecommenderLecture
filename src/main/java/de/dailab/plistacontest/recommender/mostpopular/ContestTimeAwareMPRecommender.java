package de.dailab.plistacontest.recommender.mostpopular;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.AbstractRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dailab.plistacontest.helper.DataModelHelper;
import de.dailab.plistacontest.helper.DateHelper;
import de.dailab.plistacontest.helper.Desirializer;
import de.dailab.plistacontest.helper.FalseItems;
import de.dailab.plistacontest.helper.MahoutWriter;
import de.dailab.plistacontest.helper.Serializer;
import de.dailab.plistacontest.helper.Stats;
import de.dailab.plistacontest.recommender.ContestItem;
import de.dailab.plistacontest.recommender.ContestRecommender;
import de.dailab.plistacontest.recommender.Feedback;
import de.dailab.plistacontest.recommender.Impression;

/**
 * Most Popular Recommender for the plista contest
 * 
 * @author till
 * 
 */
public class ContestTimeAwareMPRecommender implements ContestRecommender {

	private static Logger logger = LoggerFactory.getLogger(ContestTimeAwareMPRecommender.class);

	public volatile AbstractRecommender recommender;

	// recommender map
	private final Map<Integer, AbstractRecommender> domainRecommender = new HashMap<Integer, AbstractRecommender>();

	private FalseItems falseItems;

	// number of impressions before a recommender is updated
	private int impressionCount = 30;

	// number of days taken into account for the data model
	private int numberOfDays = 2;

	// don't recommend items after they were marked as invalid n times
	private int ignoreAfter = 2;

	private Properties properties = new Properties();

	private List<Long> recommendationList = new ArrayList<Long>();

	private int feedbacks = 0;

	private Stats stats;

	final private long timeframe;

	// multicounter for different recommender
	final Map<Integer, Integer> counter = new HashMap<Integer, Integer>();

	public ContestTimeAwareMPRecommender(final long _timeFrame) {
		this.timeframe = _timeFrame;
	}

	public void init() {
		// set properties
		this.impressionCount = Integer.parseInt(properties.getProperty("plista.impressionCount", "30"));

		// get all data files for the different domains
		final File dir = new File(".");
		final FileFilter fileFilter = new WildcardFileFilter("*_m_data*.txt");
		final File[] files = dir.listFiles(fileFilter);

		// get domains
		final List<Integer> domains = new ArrayList<Integer>();
		for (int i = 0; i < files.length; i++) {
			final String domainFile = files[i].getName();
			final String domainString = domainFile.substring(0, domainFile.indexOf("_"));
			final int domain = Integer.parseInt(domainString);
			if (!domains.contains(domain)) {
				domains.add(domain);
			}
		}

		// create domain MP Recommender
		for (Integer d : domains) {
			try {
				this.domainRecommender.put(d,
						new TimeAwareMostPopularItemsRecommender(DataModelHelper.getDataModel(this.numberOfDays, d),
								this.timeframe, d));
			} catch (IOException e) {
				logger.error(e.getMessage());
			} catch (TasteException e) {
				logger.error(e.getMessage());
			}
		}

		// load false items
		this.falseItems = Desirializer.<FalseItems> deserialize("falseitems.ser");
		if (this.falseItems == null) {
			this.falseItems = new FalseItems();
		}

		// load Stats
		this.stats = Desirializer.<Stats> deserialize(this.toString() + "stats.ser");
		if (this.stats == null) {
			this.stats = new Stats(this.toString());
		}
	}

	@Override
	public List<ContestItem> recommend(final Impression _impression) {
		final List<ContestItem> recList = new ArrayList<ContestItem>();
		try {

			final AbstractRecommender tmpRec = this.domainRecommender.get(_impression.getDomain());

			final List<RecommendedItem> tmp = tmpRec.recommend(_impression.getClient(), _impression.getConfig()
					.getLimit(), new MPRescorer(this.falseItems, this.ignoreAfter));

			for (RecommendedItem recommendedItem : tmp) {
				recList.add(new ContestItem(recommendedItem.getItemID()));
			}

			// update recommendationList infos
			this.recommendationList.add(_impression.getId());
			this.stats.addRecommendation(new de.dailab.plistacontest.helper.RecommendedItem(_impression.getId(),
					new Date().getTime(), recList));

		} catch (TasteException e) {
			logger.error(e.toString());
		} catch (Exception e) {
			logger.error(e.toString() + " DOMAIN: " + _impression.getDomain());
		}

		return recList;
	}

	public void impression(final Impression _impression) {

		// write info directly in MAHOUT format
		new Thread(new MahoutWriter(_impression.getDomain() + "_m_data_" + DateHelper.getDate() + ".txt",
				_impression.getJson(), 3)).start();

		// update impression counter
		if (this.counter.containsKey(_impression.getDomain())) {
			this.counter.put(_impression.getDomain(), this.counter.get(_impression.getDomain()) + 1);
		} else {
			this.counter.put(_impression.getDomain(), 1);
		}

		if (this.counter.get(_impression.getDomain()) >= this.impressionCount) {
			this.counter.put(_impression.getDomain(), 0);
			new Thread() {

				public void run() {
					update(_impression.getDomain());
				}

			}.start();
		}
	}

	public void update(final int _domain) {
		AbstractRecommender recommender = null;
		try {
			recommender = new MostPopularItemsRecommender(DataModelHelper.getDataModel(this.numberOfDays, _domain),
					Boolean.parseBoolean(properties.getProperty("plista.timeBoost", "false")), _domain);
		} catch (TasteException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		if (recommender != null) {
			synchronized (this) {
				this.domainRecommender.put(_domain, recommender);
			}
		}

		Serializer.serialize(stats, this.toString() + "_stats.ser");
	}

	@Override
	public void feedback(final Feedback _feedback) {
		try {
			// write info directly in MAHOUT format -> with pref 5
			new Thread(new MahoutWriter("m_data_" + DateHelper.getDate() + ".txt", _feedback.getClient() + ","
					+ _feedback.getTarget(), 5)).start();

			// increase feedback count if this recommender gets a positive
			// feedback
			if (_feedback.getTeamId() == Integer.parseInt(this.properties.getProperty("plista.teamId"))
					&& this.recommendationList.contains(_feedback.getTarget())) {
				this.feedbacks += 1;
				this.stats.addFeedback(_feedback);
				Serializer.serialize(this.stats, this.toString() + "_stats.ser");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void error(String _error) {
		logger.error(_error);
		// {"error":"invalid items returned:89194287","team":{"id":"65"},"code":null,"version":"1.0"}
		try {
			final JSONObject jErrorObj = (JSONObject) JSONValue.parse(_error);
			if (jErrorObj.containsKey("error")) {
				String error = jErrorObj.get("error").toString();
				if (error.contains("invalid items returned:")) {
					String tmpError = error.replace("invalid items returned:", "");
					String[] errorItems = tmpError.split(",");
					for (String errorItem : errorItems) {
						this.falseItems.addItem(Long.parseLong(errorItem));
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Serializer.serialize(this.falseItems, "falseitems.ser");
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(final Properties properties) {
		this.properties = properties;
	}

	@Override
	public double getCTR() {
		double ctr = 0;
		if (this.recommendationList.size() > 0) {
			ctr = this.feedbacks / this.recommendationList.size();
		}
		return ctr;
	}

	@Override
	public String toString() {
		return super.toString() + "_" + this.timeframe;
	}

}
