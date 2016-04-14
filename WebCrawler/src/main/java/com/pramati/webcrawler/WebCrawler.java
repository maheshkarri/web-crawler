package com.pramati.webcrawler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.factory.ServiceFactory;
import com.pramati.schedular.MonthMailsSchedular;
import com.pramati.service.MailInfoService;

public class WebCrawler {

	final String mailListLink = com.pramati.constants.WebCrawlerConstants.MAIL_LINK.getAbbrevation();

	final int requiredMailsOfYear = Integer
			.parseInt(com.pramati.constants.WebCrawlerConstants.REQUIRED_MAILS_OF_YEAR.getAbbrevation());

	private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

	public static void main(String[] args) {
		new WebCrawler();
	}

	private MailInfoService mailInfoService;

	public WebCrawler() {

		try {
			
			Date startDate = new Date(); 
			mailInfoService = (MailInfoService) ServiceFactory
					.getService(com.pramati.constants.DataServiceTypes.MAIL_INFO_SERVICE.name());

			List<String> monthLinks = mailInfoService.getMonthLinks(mailListLink, requiredMailsOfYear);

			
			for (String monthLink : monthLinks) {

				List<String> pageWiseMonthLink = mailInfoService.getPageWiseMonthLink(monthLink);
				logger.info(pageWiseMonthLink.toString());

				if (pageWiseMonthLink != null && !pageWiseMonthLink.isEmpty()) {

					for (String pageWiseLink : pageWiseMonthLink) {

						List<String> mailLinks = mailInfoService.getMonthWiseLinks(pageWiseLink);

						MonthMailsSchedular schedular = new MonthMailsSchedular(mailLinks);
						schedular.schedule();
					}
				}
			}
			
			Date end = new Date();
			
			System.out.println(end.getTime()-startDate.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}