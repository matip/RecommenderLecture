package de.dailab.plistacontest.recommender.mostpopular;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.recommender.AbstractRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dailab.plistacontest.helper.Desirializer;
import de.dailab.plistacontest.helper.Serializer;
import de.dailab.plistacontest.recommender.RecommendedItems;

public class TimeAwareMostPopularItemsRecommender extends AbstractRecommender {

	private final Logger logger = LoggerFactory.getLogger(TimeAwareMostPopularItemsRecommender.class);

	private final List<Entry<Long, Double>> mostPopluarItems = new ArrayList<Map.Entry<Long, Double>>();

	private RecommendedItems recommendedItems = new RecommendedItems();

	private int domain;

	private long timeFrame;

	public int getDomain() {
		return domain;
	}

	/**
	 * @param _dataModel
	 * @param _timeboost
	 * @param _domain
	 * 
	 * */
	public TimeAwareMostPopularItemsRecommender(final DataModel _dataModel, final long _timeFrame, final int _domain)
			throws TasteException {

		super(_dataModel);

		this.timeFrame = _timeFrame;
		// process the most popular items of the dataset amongst all users
		final List<Entry<Long, Double>> sortedList = sortByValue(countItems(_timeFrame));
		// revert list, so that the item with the most entries is on top>
		Collections.reverse(sortedList);
		this.mostPopluarItems.addAll(sortedList);
		this.domain = _domain;

		Desirializer.deserialize(this.domain + "_" + _timeFrame + "_recommendedItems.ser");
	}

	/**
	 * Counts all items in the dataset.
	 * 
	 * @param _timeaware
	 *            if true, current items are boosted
	 * @return
	 * @throws TasteException
	 */
	private Map<Long, Double> countItems(final long _timeframe) throws TasteException {

		final LongPrimitiveIterator lpi = this.getDataModel().getUserIDs();

		final Map<Long, Double> map = new TreeMap<Long, Double>();

		// iterate over users
		while (lpi.hasNext()) {
			long uid = lpi.next();
			final FastIDSet uili = this.getDataModel().getItemIDsFromUser(uid);
			final LongPrimitiveIterator iIter = uili.iterator();

			// iterate over the items
			while (iIter.hasNext()) {

				long itemId = iIter.next();

				final Long timestamp = this.getDataModel().getPreferenceTime(uid, itemId);
				if (timestamp != null && _timeframe < timestamp) {
					// count items
					if (map.containsKey(itemId)) {
						map.put(itemId, map.get(itemId));
					} else {
						map.put(itemId, 1d);
					}
				}
			}
		}

		return map;
	}

	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		FastIDSet idSet = new FastIDSet();

		try {
			idSet = this.getDataModel().getItemIDsFromUser(userID);
		} catch (org.apache.mahout.cf.taste.common.NoSuchUserException e) {
			this.logger.debug(e.toString());
		}

		final List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();

		int listKey = 0;
		for (int i = 0; i < howMany; i++) {
			if (i < this.mostPopluarItems.size()) {
				long itemId = this.mostPopluarItems.get(listKey).getKey();

				if (!idSet.contains(itemId) && !rescorer.isFiltered(itemId)
						&& !this.recommendedItems.isAlreadyRecommended(userID, itemId)) {
					final RecommendedItem recommendedItem = new GenericRecommendedItem(itemId, 5);
					recommendedItems.add(recommendedItem);
					this.recommendedItems.addItem(userID, itemId);

				} else {
					i--;
				}
				listKey++;
			} else {
				break;
			}
		}

		Serializer.serialize(this.recommendedItems, this.domain + "_" + this.timeFrame + "_recommendedItems.ser");
		return recommendedItems;
	}

	public <K, V extends Comparable<V>> List<Entry<K, V>> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> entries = new ArrayList<Entry<K, V>>(map.entrySet());
		Collections.sort(entries, new ByValue<K, V>());
		return entries;
	}

	private class ByValue<K, V extends Comparable<V>> implements Comparator<Entry<K, V>> {

		public int compare(Entry<K, V> o1, Entry<K, V> o2) {
			if (o1.getValue().compareTo(o2.getValue()) == 0) {
				try {
					if (Long.getLong(o1.getKey().toString()) < (Long.getLong(o2.getKey().toString()))) {
						return -1;
					} else {
						return 1;
					}
				} catch (Exception e) {
					return 0;
				}

			} else {
				return o1.getValue().compareTo(o2.getValue());
			}

		}
	}

	public float estimatePreference(long userID, long itemID) throws TasteException {
		return 0;
	}

	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		// TODO Auto-generated method stub
	}
}
