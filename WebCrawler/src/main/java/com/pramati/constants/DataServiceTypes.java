package com.pramati.constants;

public enum DataServiceTypes {

	MAIL_INFO_SERVICE("com.pramati.service.impl.MailInfoServiceImpl"),
	MAILS_STORAGE_SERVICE("com.pramati.service.impl.MailsStorageServiceImpl"),;
	
	
	private String abbrevation;
	
	private DataServiceTypes(String abbrevation) {
		this.abbrevation = abbrevation;
	}
	
	public String getAbbrevation(){
		return abbrevation;
	}
}
