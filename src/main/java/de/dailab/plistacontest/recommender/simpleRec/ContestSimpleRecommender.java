package de.dailab.plistacontest.recommender.simpleRec;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dailab.plistacontest.helper.Desirializer;
import de.dailab.plistacontest.helper.FalseItems;
import de.dailab.plistacontest.helper.Serializer;
import de.dailab.plistacontest.helper.Stats;
import de.dailab.plistacontest.recommender.ContestItem;
import de.dailab.plistacontest.recommender.ContestRecommender;
import de.dailab.plistacontest.recommender.Feedback;
import de.dailab.plistacontest.recommender.Impression;

/**
 * Recommends the last 25 items clicked on a certain domain.
 * 
 * @author till
 * 
 */
public class ContestSimpleRecommender implements ContestRecommender {

	private static Logger logger = LoggerFactory.getLogger(ContestSimpleRecommender.class);

	// item map
	private Map<Integer, List<Long>> domainMap = new HashMap<Integer, List<Long>>();

	private Properties properties = new Properties();

	private FalseItems falseItems;

	private int impressionCount = 30;

	private final List<Long> recommendationList = new ArrayList<Long>();

	private int feedbacks = 0;

	private Stats stats;

	//
	private final int numberOfItemsInList = 25;
	private final String statsFileName = "SimpleRecommender_stats.ser";
	private final String falseFileName = "SimpleRecommender_falseitems.ser";

	// multicounter for different recommender
	final Map<Integer, Integer> counter = new HashMap<Integer, Integer>();

	public void init() {
		logger.info("init simple rec");

		// load false items
		this.falseItems = Desirializer.<FalseItems> deserialize(this.falseFileName);
		if (this.falseItems == null) {
			this.falseItems = new FalseItems();
		}
		// load Stats
		this.stats = Desirializer.<Stats> deserialize(this.statsFileName);
		if (this.stats == null) {
			this.stats = new Stats("SimpleRecommender");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dailab.plistacontest.recommender.ContestRecommender#recommend(de.dailab
	 * .plistacontest.recommender.Impression)
	 */
	@Override
	public List<ContestItem> recommend(final Impression _impression) {
		final List<ContestItem> recList = new ArrayList<ContestItem>();
		try {
			final List<Long> items = this.domainMap.get(_impression.getDomain());
			for (Long iId : items) {
				if (iId != _impression.getId() && !containsItem(recList, iId) && !this.falseItems.containsItem(iId)) {
					recList.add(new ContestItem(iId));
				}
				if (recList.size() == _impression.getConfig().getLimit()) {
					break;
				}
			}

			try {
				// update recommendationList infos
				this.recommendationList.add(_impression.getId());
				this.stats.addRecommendation(new de.dailab.plistacontest.helper.RecommendedItem(_impression.getId(),
						new Date().getTime(), recList));
			} catch (Exception e) {
				logger.error(e.toString());
			}

		} catch (Exception e) {
			logger.error(e.toString() + " DOMAIN: " + _impression.getDomain());
		}

		return recList;
	}

	private boolean containsItem(List<ContestItem> _recItems, long _itemId) {
		for (ContestItem item : _recItems) {
			if (item.getId() == _itemId) {
				return true;
			}
		}
		return false;
	}

	public void impression(final Impression _impression) {
		try {
			final List<Long> i = new ArrayList<Long>();
			if (!this.domainMap.containsKey(_impression.getDomain())) {
				i.add(_impression.getItem().getId());
				this.domainMap.put(_impression.getDomain(), i);
			} else {
				i.addAll(this.domainMap.get(_impression.getDomain()));
				if (!containsId(i, _impression.getItem().getId())
						&& !this.falseItems.containsItem(_impression.getItem().getId())) {
					if (i.size() < this.numberOfItemsInList) {
						i.add(_impression.getItem().getId());
					} else {
						i.remove(new Random().nextInt(this.numberOfItemsInList));
						i.add(_impression.getItem().getId());
					}
					this.domainMap.put(_impression.getDomain(), i);
				}
			}

			impressionCount--;
			if (impressionCount == 0) {
				Serializer.serialize(this.stats, this.statsFileName);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private boolean containsId(List<Long> _recIds, long _itemId) {
		for (Long id : _recIds) {
			if (id == _itemId) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void feedback(final Feedback _feedback) {
		logger.info(_feedback.getJson());
		try {

			// increase feedback count if this recommender gets a positive
			// feedback
			if (_feedback.getTeamId() == Integer.parseInt(this.properties.getProperty("plista.teamId"))
					&& this.recommendationList.contains(_feedback.getTarget())) {
				this.feedbacks += 1;
				this.stats.addFeedback(_feedback);
				Serializer.serialize(this.stats, this.statsFileName);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void error(String _error) {
		logger.error(_error);
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
		Serializer.serialize(this.falseItems, this.falseFileName);
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
	public void update(int _domain) {
		// TODO Auto-generated method stub

	}

}
