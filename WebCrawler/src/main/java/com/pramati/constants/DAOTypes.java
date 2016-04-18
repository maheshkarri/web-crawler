package com.pramati.constants;

public enum DAOTypes {

	MAIL_INFO_DAO("com.pramati.dao.impl.MailInfoDAOImpl"),
	MAILS_STORAGE_DAO("com.pramati.dao.impl.MailsStorageDAOImpl"),;
	
	
	private String abbrevation;
	
	private DAOTypes(String abbrevation) {
		this.abbrevation = abbrevation;
	}
	
	public String getAbbrevation(){
		return abbrevation;
	}
}
