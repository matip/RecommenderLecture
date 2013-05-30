package de.dailab.plistacontest.helper;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dailab.plistacontest.recommender.Config;
import de.dailab.plistacontest.recommender.Impression;
import de.dailab.plistacontest.recommender.Item;

/**
 * @author till
 *
 */
public class ImpressionHandler {

	private static Logger logger = LoggerFactory.getLogger(ImpressionHandler.class);
	
	/**
	 * @param _jsonString
	 * @return
	 */
	public final static Impression getImpression(final String _jsonString) {
		final Impression imp = new Impression();
		final JSONObject jObj = (JSONObject) JSONValue.parse(_jsonString);	
		imp.setJson(_jsonString);
		// parse fields
		try {
			imp.setId(Integer.parseInt(jObj.get("id").toString()));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			final String client = ((JSONObject) jObj.get("client")).get("id").toString();
			imp.setClient(Long.parseLong(client));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			final String domain = ((JSONObject) jObj.get("domain")).get("id").toString();
			imp.setDomain(Integer.parseInt(domain));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			final Item item = ItemHandler.getItem(((JSONObject) jObj.get("item")).toString());
			imp.setItem(item);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		try {
			final Config config = ConfigHandler.getConfig(((JSONObject) jObj.get("config")).toString());
			imp.setConfig(config);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		try {
			imp.setVersion( jObj.get("version").toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return imp;
	}

}
