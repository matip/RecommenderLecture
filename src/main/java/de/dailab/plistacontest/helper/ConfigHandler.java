package de.dailab.plistacontest.helper;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dailab.plistacontest.recommender.Config;

public class ConfigHandler {

	private static Logger logger = LoggerFactory.getLogger(ConfigHandler.class);

	final static Config getConfig(final String _jsonString) {
		final Config config = new Config();
		final JSONObject jObj = (JSONObject) JSONValue.parse(_jsonString);

		try {
			config.setLimit(Integer.parseInt(jObj.get("limit").toString()));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			config.setRecommendable(Boolean.parseBoolean(jObj.get("recommend").toString()));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			config.setTimeout(jObj.get("timeout").toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return config;
	}
}
