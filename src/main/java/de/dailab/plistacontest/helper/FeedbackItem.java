package de.dailab.plistacontest.helper;

import java.io.Serializable;

public class FeedbackItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8288719017954711267L;
	
	long recomemendationID;
	long timestamp;
	long itemId;
	long clientId;
	
	/**
	 * @return the recomemendationID
	 */
	public long getRecomemendationID() {
		return recomemendationID;
	}
	/**
	 * @param recomemendationID the recomemendationID to set
	 */
	public void setRecomemendationID(long recomemendationID) {
		this.recomemendationID = recomemendationID;
	}
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the itemId
	 */
	public long getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the clientId
	 */
	public long getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
}
