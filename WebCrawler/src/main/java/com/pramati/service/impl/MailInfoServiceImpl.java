package com.pramati.service.impl;
import java.util.List;

import org.jsoup.nodes.Element;

import com.pramati.beans.MailInfo;
import com.pramati.dao.MailInfoDAO;
import com.pramati.exception.WebCrawlerException;
import com.pramati.factory.DAOFactory;
import com.pramati.service.MailInfoService;

public class MailInfoServiceImpl implements MailInfoService {
	
	private MailInfoDAO mailInfoDAO;
	
	public MailInfoServiceImpl() {
		mailInfoDAO = (MailInfoDAO) DAOFactory.getDAO(com.pramati.constants.DAOTypes.MAIL_INFO_DAO.name());
	}

	@Override
	public List<String> getMonthLinks(String link, int requiredYear) throws WebCrawlerException{
		return mailInfoDAO.fetchMonthLinks(link, requiredYear);
	}

	@Override
	public List<String> getMonthWiseLinks(String pageWiseMonthLink) throws WebCrawlerException{
		return mailInfoDAO.fetchMonthWiseLinks(pageWiseMonthLink);
	}

	@Override
	public MailInfo getMailInfo(String mailLink) throws WebCrawlerException{
		return mailInfoDAO.fetchMailInfo(mailLink);
	}

	@Override
	public List<String> getPageWiseMonthLink(String monthLink) throws WebCrawlerException {
		return mailInfoDAO.getPageWiseMonthLink(monthLink);
	}
	
	
}
