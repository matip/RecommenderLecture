package de.dailab.plistacontest.helper;

import java.io.Serializable;
import java.util.List;

import de.dailab.plistacontest.recommender.ContestItem;

/**
 * @author till
 * 
 */
public class RecommendedItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 765238486514194590L;

	long recommendationID;
	long timestamp;
	List<ContestItem> recommendedItems;

	public RecommendedItem(long recId, long timestamp, List<ContestItem> _recs) {
		this.recommendationID = recId;
		this.timestamp = timestamp;
		this.recommendedItems = _recs;
	}

	/**
	 * @return the recomemendationID
	 */
	public long getRecomemendationID() {
		return recommendationID;
	}

	/**
	 * @param recomemendationID
	 *            the recomemendationID to set
	 */
	public void setRecomemendationID(long recomemendationID) {
		this.recommendationID = recomemendationID;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the recommendedItems
	 */
	public List<ContestItem> getRecommendedItems() {
		return recommendedItems;
	}

	/**
	 * @param recommendedItems
	 *            the recommendedItems to set
	 */
	public void setRecommendedItems(List<ContestItem> recommendedItems) {
		this.recommendedItems = recommendedItems;
	}
}
