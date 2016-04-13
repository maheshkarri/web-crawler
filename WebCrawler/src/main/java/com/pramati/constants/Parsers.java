package com.pramati.constants;

public enum Parsers {
	MAIL_PARSER("com.pramati.webcrawler.parser.MailParser");
	
	
	private String abbrevation;
	
	private Parsers(String abbrevation) {
		this.abbrevation = abbrevation;
	}
	
	public String getAbbrevation(){
		return abbrevation;
	}
	
}
