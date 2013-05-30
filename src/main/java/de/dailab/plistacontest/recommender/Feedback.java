package de.dailab.plistacontest.recommender;

public class Feedback {

	long client;
	int domain;
	int source;
	long target;
	int teamId =  -1;
	String json;
	
	/**
	 * @return the client
	 */
	public long getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(long client) {
		this.client = client;
	}
	/**
	 * @return the domain
	 */
	public int getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(int domain) {
		this.domain = domain;
	}
	/**
	 * @return the source
	 */
	public int getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(int source) {
		this.source = source;
	}
	/**
	 * @return the target
	 */
	public long getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(long target) {
		this.target = target;
	}
	/**
	 * @return the teamId
	 */
	public int getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return the json
	 */
	public String getJson() {
		return json;
	}
	/**
	 * @param json the json to set
	 */
	public void setJson(String json) {
		this.json = json;
	}
	
	
}
