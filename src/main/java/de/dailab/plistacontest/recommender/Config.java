package de.dailab.plistacontest.recommender;

public class Config {
	
	String timeout;
	boolean recommendable;
	int limit;
	
	/**
	 * @return the timeout
	 */
	public String getTimeout() {
		return timeout;
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	/**
	 * @return the recommend
	 */
	public boolean isRecommendable() {
		return recommendable;
	}
	/**
	 * @param recommend the recommend to set
	 */
	public void setRecommendable(boolean recommend) {
		this.recommendable = recommend;
	}
	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
