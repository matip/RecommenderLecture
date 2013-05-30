package de.dailab.plistacontest.helper;

import java.io.Serializable;
import java.util.List;

import de.dailab.plistacontest.recommender.Feedback;

public class Stats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 579309064624824296L;

	private String recommenderIdentifier;
	private List<RecommendedItem> recommendation;
	private List<Feedback> feedback;

	public Stats(final String _recIdentifier) {
		this.recommenderIdentifier = _recIdentifier;
	}

	/**
	 * @return the recommendation
	 */
	public List<RecommendedItem> getRecommendation() {
		return recommendation;
	}

	/**
	 * @param recommendation
	 *            the recommendation to set
	 */
	public void setRecommendation(List<RecommendedItem> recommendation) {
		this.recommendation = recommendation;
	}

	/**
	 * @return the feedback
	 */
	public List<Feedback> getFeedback() {
		return feedback;
	}

	/**
	 * @param feedback
	 *            the feedback to set
	 */
	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}

	/**
	 * @return the recommenderIdentifier
	 */
	public String getRecommenderIdentifier() {
		return recommenderIdentifier;
	}

	/**
	 * @param recommenderIdentifier
	 *            the recommenderIdentifier to set
	 */
	public void setRecommenderIdentifier(String recommenderIdentifier) {
		this.recommenderIdentifier = recommenderIdentifier;
	}

	public void addFeedback(Feedback _feedback) {
		this.feedback.add(_feedback);
	}
	
	public void addRecommendation(RecommendedItem _recommendedItem) {
		this.recommendation.add(_recommendedItem);
	}
}
