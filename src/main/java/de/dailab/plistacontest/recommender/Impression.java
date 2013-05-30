package de.dailab.plistacontest.recommender;

/**
 * Contest Impression send from contest server
 * 
 * @author till
 *
 */
public class Impression {
	
	long id;
	long client;
	int domain;
	Item item;
	Config config;
	String version;
	String json;
	
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
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	/**
	 * @return the config
	 */
	public Config getConfig() {
		return config;
	}
	/**
	 * @param config the config to set
	 */
	public void setConfig(Config config) {
		this.config = config;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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

	/*
	 * {"msg":"impression","id":10462595,"client":{"id":6382444422},"domain":{"id":418},"item":{"id":"85303311136","title":"\u00c4rger auf dem Stra\u00ad\u00dfen\u00adstrich","url":"http:\/\/www.ksta.de\/koeln-uebersicht\/prostitution-aerger-auf-dem-strassenstrich,16341264,12510144.html","created":1340167486,"text":"Im K\u00f6lner S\u00fcden tobt eine Ausein\u00adan\u00adder\u00adset\u00adzung unter Prosti\u00adtu\u00adierten: Die heimi\u00adschen Dirnen f\u00fchlen sich von Bulga\u00adrinnen und deren Anhang verdr\u00e4ngt. Mit der geplanten Auswei\u00adtung des Sperr\u00adbe\u00adzirks wird sich die Lage wohl versch\u00e4rfen.","img":null,"recommendable":true},"config":{"timeout":null,"recommend":true,"limit":4},"version":"1.0"}' http://localhost:8080/ -w %{time_connect}:%{time_starttransfer}:%{time_total}
	 */

}
