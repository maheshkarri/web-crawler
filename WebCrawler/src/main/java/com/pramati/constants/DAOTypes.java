package com.pramati.constants;

public enum DAOTypes {

	MAIL_INFO_DAO("com.pramati.webcrawler.service.impl.MailInfoDAOImpl");
	
	
	private String abbrevation;
	
	private DAOTypes(String abbrevation) {
		this.abbrevation = abbrevation;
	}
	
	public String getAbbrevation(){
		return abbrevation;
	}
}
