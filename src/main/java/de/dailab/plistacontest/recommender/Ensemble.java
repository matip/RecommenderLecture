package de.dailab.plistacontest.recommender;

import java.util.List;


public interface Ensemble {
	
	
	void init(List<ContestRecommender> contestRecommenders);
	
	ContestRecommender getRecommender();

	List<ContestItem> getRecommendations(final Impression _impression);
	
	void update(List<ContestRecommender> contestRecommenders);
	
}
