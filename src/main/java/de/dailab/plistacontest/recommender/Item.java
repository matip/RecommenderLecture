package de.dailab.plistacontest.recommender;

/**
 * Item
 * 
 * @author till
 *
 */
public class Item {

	long id;
	String title;
	String url;
	long created;
	String text;
	String img;
	boolean recommendable;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the created
	 */
	public long getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(long created) {
		this.created = created;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}
	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * @return the recommendable
	 */
	public boolean isRecommendable() {
		return recommendable;
	}
	/**
	 * @param recommendable the recommendable to set
	 */
	public void setRecommendable(boolean recommendable) {
		this.recommendable = recommendable;
	}
	
	
	
}
