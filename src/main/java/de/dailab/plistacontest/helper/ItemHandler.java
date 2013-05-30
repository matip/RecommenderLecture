package de.dailab.plistacontest.helper;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dailab.plistacontest.recommender.Item;

/**
 * @author till
 * 
 */
public class ItemHandler {

	private final static Logger logger = LoggerFactory.getLogger(ItemHandler.class);

	/**
	 * @param _jsonString
	 * @return
	 */
	static Item getItem(final String _jsonString) {
		final JSONObject jObj = (JSONObject) JSONValue.parse(_jsonString);
		final Item item = new Item();

		try {
			String i = jObj.get("id").toString();
			item.setId(Long.parseLong(i));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			item.setTitle(jObj.get("title").toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			item.setText(jObj.get("text").toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			item.setUrl(jObj.get("url").toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			item.setCreated(Long.parseLong(jObj.get("created").toString()));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			item.setImg(jObj.get("img").toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			item.setRecommendable(Boolean.parseBoolean(jObj.get("recommendable").toString()));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return item;
	}

}
