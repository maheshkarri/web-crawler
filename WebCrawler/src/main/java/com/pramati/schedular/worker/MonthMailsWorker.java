package com.pramati.schedular.worker;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.exception.WebCrawlerException;
import com.pramati.factory.ServiceFactory;
import com.pramati.service.MailInfoService;

public class MonthMailsWorker implements Callable<MailInfo> {

	
	
	private String mailLink = null;

	private static final Logger logger = LoggerFactory.getLogger(MonthMailsWorker.class);
	
	private static MailInfoService mailInfoService = (MailInfoService) ServiceFactory.getService(com.pramati.constants.DataServiceTypes.MAIL_INFO_SERVICE.name());

	public MonthMailsWorker(String mailLink) {
		this.mailLink = mailLink;
	}

	@Override
	public MailInfo call() throws WebCrawlerException {
		try{
			
			MailInfo mailInfo = mailInfoService.getMailInfo(mailLink);
			
			return mailInfo;
		}catch(Exception e){
			throw new WebCrawlerException(e.getMessage());
		}
	}
}
