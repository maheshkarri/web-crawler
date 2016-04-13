package com.pramati.constants;

public enum DataServiceTypes {

	MAIL_INFO_SERVICE("com.pramati.webcrawler.service.impl.MailInfoServiceImpl");
	
	
	private String abbrevation;
	
	private DataServiceTypes(String abbrevation) {
		this.abbrevation = abbrevation;
	}
	
	public String getAbbrevation(){
		return abbrevation;
	}
}
