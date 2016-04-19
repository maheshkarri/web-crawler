package com.pramati.webcrawler;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.beans.MonthMailsInfo;
import com.pramati.factory.ServiceFactory;
import com.pramati.schedular.MonthMailsSchedular;
import com.pramati.service.MailInfoService;
import com.pramati.service.MailsStorageService;

public class WebCrawler {

	final String mailListLink = com.pramati.constants.WebCrawlerConstants.MAIL_LINK.getAbbrevation();

	final int requiredMailsOfYear = Integer
			.parseInt(com.pramati.constants.WebCrawlerConstants.REQUIRED_MAILS_OF_YEAR.getAbbrevation());

	private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

	public static void main(String[] args) {
		new WebCrawler();
	}

	private MailInfoService mailInfoService;
	private MailsStorageService mailsStorageService;

	public WebCrawler() {

		try {

			long startDate = System.currentTimeMillis();

			logger.info("Web Crawler running started : " + Calendar.getInstance().getTime().toString());

			mailInfoService = (MailInfoService) ServiceFactory
					.getService(com.pramati.constants.DataServiceTypes.MAIL_INFO_SERVICE.name());

			mailsStorageService = (MailsStorageService) ServiceFactory
					.getService(com.pramati.constants.DataServiceTypes.MAILS_STORAGE_SERVICE.name());

			logger.info("Mails Root Link " + mailListLink + "  year to download " + requiredMailsOfYear);

			List<MonthMailsInfo> monthLinks = mailInfoService.getMonthLinks(mailListLink, requiredMailsOfYear);

			MonthMailsSchedular schedular = MonthMailsSchedular.getInstance();

			for (MonthMailsInfo monthLink : monthLinks) {

				boolean proceed = true;

				boolean isMailDirectoryExists = false;

				logger.info("Processing for the month " + monthLink.getMonth());

				if (!mailsStorageService.isMailDirectoryExists(monthLink.getMonth())) {
					isMailDirectoryExists = mailsStorageService.createMailDirectory(monthLink.getMonth());

				} else {
					isMailDirectoryExists = true;
				}

				if (isMailDirectoryExists) {
					int mailsCount = mailsStorageService.getMailsCount(monthLink.getMonth());
					logger.info("Already downloaded Count " + mailsCount + " Acutal Mails Count "
							+ monthLink.getMsgCount());
					if (mailsCount == monthLink.getMsgCount()) {
						proceed = false;
					}
				}

				if (proceed) {

					Map<Integer, String> pageWiseMonthLink = mailInfoService
							.getPageWiseMonthLink(monthLink.getMonthLink());

					if (pageWiseMonthLink != null && !pageWiseMonthLink.isEmpty()) {
						logger.info("Total Pages " + pageWiseMonthLink.size());
						for (Map.Entry<Integer, String> entry : pageWiseMonthLink.entrySet()) {

							logger.info("Processing Page " + entry.getKey());

							List<MailInfo> monthWiseLinks = mailInfoService.getMonthWiseLinks(entry.getValue());

							if (monthWiseLinks != null && !monthWiseLinks.isEmpty()) {
								
								int totalMailsPerPage = monthWiseLinks.size();
								
								int mailsToBeDownload = totalMailsPerPage;
								
								logger.info("Total Mails for Page "+totalMailsPerPage);
								
								while(mailsToBeDownload > 0){
									if(mailsToBeDownload > 20){
										mailsToBeDownload -= 20;
									}else{
										mailsToBeDownload = 0;
									}
								}
								
								for (MailInfo mailInfo : monthWiseLinks) {

									String date = mailInfo.getDate();

									boolean mailExistsInDirectory = mailsStorageService
											.isMailExistsInDirectory(monthLink.getMonth(), date);

									if (!mailExistsInDirectory) {

										mailInfo = schedular.schedule(mailInfo.getMailBodyLink());

										mailInfo.setDate(date);

										boolean createMailFile = mailsStorageService
												.createMailFile(monthLink.getMonth(), mailInfo);
										if (createMailFile) {
											logger.info("created");
										}
									}
								}
							}

						}
					}
				}
			}

			long end = System.currentTimeMillis();

			logger.info("Total Running time " + ((double) (end - startDate) / 1000));

			schedular.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}