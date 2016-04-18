package com.pramati.service.impl;

import com.pramati.beans.MailInfo;
import com.pramati.dao.MailsStorageDAO;
import com.pramati.factory.DAOFactory;
import com.pramati.service.MailsStorageService;

public class MailsStorageServiceImpl implements MailsStorageService {

	private MailsStorageDAO mailsStorageDAO;
	
	public MailsStorageServiceImpl() {
		mailsStorageDAO = (MailsStorageDAO)DAOFactory.getDAO(com.pramati.constants.DAOTypes.MAILS_STORAGE_DAO.name());
	}
	@Override
	public boolean isMailDirectoryExists(String directoryName) {
		return mailsStorageDAO.isMailDirectoryExists(directoryName);
	}

	@Override
	public boolean createMailDirectory(String directoryName) {
		return mailsStorageDAO.createMailDirectory(directoryName);
	}

	@Override
	public int getMailsCount(String directoryName) {
		return mailsStorageDAO.getMailsCount(directoryName);
	}

	@Override
	public boolean isMailExistsInDirectory(String directoryName, String mailFileName) {
		return mailsStorageDAO.isMailExistsInDirectory(directoryName, mailFileName);
	}

	@Override
	public boolean createMailFile(String directoryName, MailInfo mailInfo) {
		return mailsStorageDAO.createMailFile(directoryName, mailInfo);
	}

}
