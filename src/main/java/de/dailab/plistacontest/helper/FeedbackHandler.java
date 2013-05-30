package de.dailab.plistacontest.helper;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dailab.plistacontest.recommender.Feedback;

/**
 * @author till
 *
 */
public class FeedbackHandler {

	
	private final static Logger logger = LoggerFactory.getLogger(FeedbackHandler.class);
	
	//{"msg":"feedback","client":{"id":"1976853559"},"domain":{"id":"8143"},"source":{"id":"7602"},"target":{"id":67671284},"version":"1.0"},2013-03-27 09:26:16,720
	//{"msg":"feedback","client":{"id":"1976986295"},"domain":{"id":"1677"},"source":{"id":"748"},"target":{"id":120486366},"config":{"team":{"id":"65"}},"version":"1.0"},2013-03-27 10:41:23,687

	/**
	 * @param _jsonString
	 * @return
	 */
	public final static Feedback getFeedback(final String _jsonString) {
		final Feedback feed = new Feedback();
		final JSONObject jObj = (JSONObject) JSONValue.parse(_jsonString);	
		feed.setJson(_jsonString);
		
		//parse fields
		try {
			final String client = ((JSONObject) jObj.get("client")).get("id").toString();
			feed.setClient(Long.parseLong(client));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			final String domain = ((JSONObject) jObj.get("domain")).get("id").toString();
			feed.setDomain(Integer.parseInt(domain));
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		try {
			final String source = ((JSONObject) jObj.get("source")).get("id").toString();
			feed.setSource(Integer.parseInt(source));
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		
		try {
			final String target = ((JSONObject) jObj.get("target")).get("id").toString();
			feed.setTarget(Long.parseLong(target));
		} catch (Exception e) {
			logger.error(e.toString());
		}

		try {
			final String team = ( (JSONObject) ((JSONObject) jObj.get("config")).get("team")).get("id").toString();
			feed.setTeamId(Integer.parseInt(team));
		} catch (Exception e) {
			logger.error(e.toString());
		}
		

		return feed;
	}

}
