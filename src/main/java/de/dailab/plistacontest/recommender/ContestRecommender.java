package de.dailab.plistacontest.recommender;

import java.util.List;
import java.util.Properties;

import de.dailab.plistacontest.client.ContestHandler;

/**
 * @author till
 * 
 */
public interface ContestRecommender {

	/**
	 * This method is colled by the contest handler at startup
	 */
	public void init();
	
	/**
	 * Recommend method called by {@link ContestHandler}
	 * 
	 * @param _impression
	 * @return
	 */
	public List<ContestItem> recommend(final Impression _impression);


	/**
	 * Impression Information from the Plista server is send to this method
	 * 
	 * @param _impression
	 */
	public void impression(final Impression _impression);

	/**
	 * Impression Information from the Plista server is send to this method
	 * 
	 * @param _feedback
	 */
	public void feedback(final Feedback _feedback);

	public void update(final int _domain);
	
	/**
	 * Impression Information from the Plista server is send to this method
	 * 
	 * @param _error
	 */
	public void error(final String _error);

	public void setProperties(Properties properties);
	
	public double getCTR();

}
