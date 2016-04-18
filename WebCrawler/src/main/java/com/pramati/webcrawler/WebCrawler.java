package com.pramati.webcrawler;

import java.util.Date;
import java.util.List;

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
			mailInfoService = (MailInfoService) ServiceFactory
					.getService(com.pramati.constants.DataServiceTypes.MAIL_INFO_SERVICE.name());

			mailsStorageService = (MailsStorageService) ServiceFactory
					.getService(com.pramati.constants.DataServiceTypes.MAILS_STORAGE_SERVICE.name());

			List<MonthMailsInfo> monthLinks = mailInfoService.getMonthLinks(mailListLink, requiredMailsOfYear);

			MonthMailsSchedular schedular = MonthMailsSchedular.getInstance();

			for (MonthMailsInfo monthLink : monthLinks) {

				boolean proceed = true;

				boolean isMailDirectoryExists = false;

				if (!mailsStorageService.isMailDirectoryExists(monthLink.getMonth())) {
					isMailDirectoryExists = mailsStorageService.createMailDirectory(monthLink.getMonth());

				} else {
					isMailDirectoryExists = true;
				}

				if (isMailDirectoryExists) {
					int mailsCount = mailsStorageService.getMailsCount(monthLink.getMonth());
					logger.info("Already downloaded Count " + mailsCount);
					if (mailsCount == monthLink.getMsgCount()) {
						proceed = false;
					}
				}

				if (proceed) {

					List<String> pageWiseMonthLink = mailInfoService.getPageWiseMonthLink(monthLink.getMonthLink());


					if (pageWiseMonthLink != null && !pageWiseMonthLink.isEmpty()) {

						for (String pageWiseLink : pageWiseMonthLink) {

							List<MailInfo> monthWiseLinks = mailInfoService.getMonthWiseLinks(pageWiseLink);

							for (MailInfo mailInfo : monthWiseLinks) {

								String date = mailInfo.getDate();

								boolean mailExistsInDirectory = mailsStorageService
										.isMailExistsInDirectory(monthLink.getMonth(), date);

								if (!mailExistsInDirectory) {
									
									
									
									mailInfo = schedular.schedule(mailInfo.getMailBodyLink());
									
									mailInfo.setDate(date);
									
									boolean createMailFile = mailsStorageService.createMailFile(monthLink.getMonth(), mailInfo);
									if(createMailFile){
										logger.info("created");
									}
								}
							}

						}
					}
				}
			}

			long end = System.currentTimeMillis();

			logger.info("Total Running time "+((double)(end - startDate)/1000));
			
			schedular.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}