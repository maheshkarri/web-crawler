package com.pramati.constants;

public enum WebCrawlerConstants {
	MAIL_LINK("http://mail-archives.apache.org/mod_mbox/maven-users/"),
	REQUIRED_MAILS_OF_YEAR("2014"),;
	
	private String abbrevation;
	private WebCrawlerConstants(String abbrevation) {
		this.abbrevation = abbrevation;
	}
	
	public String getAbbrevation(){
		return abbrevation;
	}
}
