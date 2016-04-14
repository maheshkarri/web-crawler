package com.pramati.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.dao.MailInfoDAO;
import com.pramati.exception.WebCrawlerException;
import com.pramati.utils.ParserUtils;

public class MailInfoDAOImpl implements MailInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(MailInfoDAOImpl.class);

	@Override
	public List<String> fetchMonthLinks(String link, int requiredYear) throws WebCrawlerException {
		logger.info(link + " " + requiredYear);

		List<String> yearwiseLinksList = null;
		Document document = ParserUtils.getRootDocument(link);

		if (document != null) {
			Element body = ParserUtils.getElement(document, "body").get(0);

			if (body != null) {
				Elements monthLinksTag = body.select("a[href *=" + requiredYear + "]");
				if (monthLinksTag != null && monthLinksTag.size() > 0) {
					yearwiseLinksList = new ArrayList<String>();
					for (Element monthLinkTag : monthLinksTag) {
						if (ParserUtils.getAttributeValue(monthLinkTag, "href").contains("thread")) {
							yearwiseLinksList.add(ParserUtils.getAbsoluteHrefUri(monthLinkTag));
						}
					}
					logger.info("Successfully getting all month links");
				} else {
					throw new WebCrawlerException("No month links found for the year " + requiredYear);
				}
			}

		} else {
			throw new WebCrawlerException("invalid link " + link);
		}

		return yearwiseLinksList;

	}
	
	@Override
	public List<String> getPageWiseMonthLink(String monthLink) throws WebCrawlerException {
		List<String> pageWiseMonthLink = null;
		
		Document document = ParserUtils.getRootDocument(monthLink);

		if (document != null) {
			Element bodyTag = ParserUtils.getElement(document, "body").get(0);

			Element monthwise = bodyTag.select("table[id *=msglist]").get(0);
			
			pageWiseMonthLink = new ArrayList<>();
			pageWiseMonthLink.add(monthLink);

			if (monthwise != null) {
				Elements pagesTag = monthwise.select("th[class *=pages]");
				if(pagesTag != null && pagesTag.size() >0){
					Elements aTags = pagesTag.get(0).select("a[href]");
					
					if(aTags != null && aTags.size()>0){
						for (Element aTag : aTags) {
							if(!aTag.text().contains("Next"))
							{	
								pageWiseMonthLink.add(aTag.attr("abs:href"));
								
							}
						}
					}
				}
			}	
		}else {
			throw new WebCrawlerException("invalid mailLink " + monthLink);
		}

		
		return pageWiseMonthLink;
	}
	
	

	@Override
	public List<String> fetchMonthWiseLinks(String pageWiseMonthLink) throws WebCrawlerException {
		Document document = ParserUtils.getRootDocument(pageWiseMonthLink);

		List<String> monthWiseLinks = null;

		if (document != null) {

			Element bodyTag = ParserUtils.getElement(document, "body").get(0);

			Element monthwise = bodyTag.select("table[id *=msglist]").get(0);
			

			if (monthwise != null) {
				Elements messageLinks = monthwise.select("td[class *=subject]");
				if (messageLinks != null && messageLinks.size() > 0) {
					monthWiseLinks = new ArrayList<>();
					for (Element messageLink : messageLinks) {
						Elements aTags = messageLink.select("a[href]");
						if (aTags != null && aTags.size() > 0) {
							Element select = aTags.get(0);
							monthWiseLinks.add(select.attr("abs:href"));
						}
					}
				} else {
					throw new WebCrawlerException("No messages found for " + pageWiseMonthLink);
				}
			}

		} else {
			throw new WebCrawlerException("invalid pageWiseMonthlink " + pageWiseMonthLink);
		}

		return monthWiseLinks;
	}

	@Override
	public MailInfo fetchMailInfo(String mailLink) throws WebCrawlerException {
		MailInfo mailInfo = null;

		Document document = ParserUtils.getRootDocument(mailLink);

		if (document != null) {
			
			Element bodyTag = ParserUtils.getElement(document, "tbody").get(0);
			String contents = ParserUtils.getElementByCSSClassName(bodyTag, "tr", "contents").get(0).text();
			String from = ParserUtils.getElementByCSSClassName(bodyTag, "tr", "from").get(0).text();
			String subject = ParserUtils.getElementByCSSClassName(bodyTag, "tr", "subject").get(0).text();
			String date = ParserUtils.getElementByCSSClassName(bodyTag, "tr", "date").get(0).text();

			mailInfo = new MailInfo();

			mailInfo.setAuthor(from);
			mailInfo.setDate(date);
			mailInfo.setMailBody(contents);
			mailInfo.setSubject(subject);
			mailInfo.setMailBodyLink(mailLink);

		} else {
			throw new WebCrawlerException("invalid mailLink " + mailLink);
		}

		return mailInfo;
	}

	
}
