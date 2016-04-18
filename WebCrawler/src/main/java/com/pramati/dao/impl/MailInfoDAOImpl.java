package com.pramati.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.beans.MonthMailsInfo;
import com.pramati.dao.MailInfoDAO;
import com.pramati.exception.WebCrawlerException;
import com.pramati.utils.ParserUtils;

public class MailInfoDAOImpl implements MailInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(MailInfoDAOImpl.class);

	@Override
	public List<MonthMailsInfo> fetchMonthLinks(String link, int requiredYear) throws WebCrawlerException {
		logger.info(link + " " + requiredYear);

		List<MonthMailsInfo> yearwiseLinksList = null;
		Document document = ParserUtils.getRootDocument(link);

		if (document != null) {
			Element body = ParserUtils.getElement(document, "body").get(0);

			if (body != null) {

				Elements tablesList = ParserUtils.getElementByCSSClassName(body, "table", "year");
				if (tablesList != null && tablesList.size() > 0) {

					for (Element table : tablesList) {
						boolean proceed = false;

						Element thTag = ParserUtils.getElement(table, "th").get(0);
						if (thTag.text().contains(String.valueOf(requiredYear))) {
							proceed = true;
						}
						if (proceed) {

							Element tableBodyTag = ParserUtils.getElement(table, "tbody").get(0);
							if (tableBodyTag != null) {
								Elements trTags = ParserUtils.getElement(tableBodyTag, "tr");

								if (trTags != null && trTags.size() > 0) {
									yearwiseLinksList = new ArrayList<MonthMailsInfo>();
									for (Element trTag : trTags) {
							
										Element linksTag = ParserUtils.getElementByCSSClassName(trTag, "td", "links")
												.get(0);
										if (linksTag != null) {
											Element aTag = linksTag.select("a[href *=" + requiredYear + "]").get(0);
											if (aTag != null) {

												if (ParserUtils.getAttributeValue(aTag, "href").contains("thread")) {
													MonthMailsInfo monthMailsInfo = new MonthMailsInfo();
													monthMailsInfo.setMonthLink(ParserUtils.getAbsoluteHrefUri(aTag));
													String date = ParserUtils
															.getElementByCSSClassName(trTag, "td", "date").get(0)
															.text();
													int msgCount = Integer.parseInt(ParserUtils
															.getElementByCSSClassName(trTag, "td", "msgcount").get(0)
															.text());
													monthMailsInfo.setMonth(date);
													monthMailsInfo.setMsgCount(msgCount);

													yearwiseLinksList.add(monthMailsInfo);

												}
											}
										}

									}
								}
							}
						}
					}

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
				if (pagesTag != null && pagesTag.size() > 0) {
					Elements aTags = pagesTag.get(0).select("a[href]");

					if (aTags != null && aTags.size() > 0) {
						for (Element aTag : aTags) {
							if (!aTag.text().contains("Next")) {
								pageWiseMonthLink.add(aTag.attr("abs:href"));

							}
						}
					}
				}
			}
		} else {
			throw new WebCrawlerException("invalid mailLink " + monthLink);
		}

		return pageWiseMonthLink;
	}

	@Override
	public List<MailInfo> fetchMonthWiseLinks(String pageWiseMonthLink) throws WebCrawlerException {
		Document document = ParserUtils.getRootDocument(pageWiseMonthLink);

		List<MailInfo> monthWiseLinks = null;

		if (document != null) {

			Element bodyTag = ParserUtils.getElement(document, "body").get(0);

			Element monthwise = ParserUtils.getElementByCSSIdAttribute(bodyTag, "table", "msglist").get(0);

			if (monthwise != null) {
				Element tbodyTag = ParserUtils.getElement(monthwise, "tbody").get(0);

				if (tbodyTag != null) {
					Elements trTags = ParserUtils.getElement(tbodyTag, "tr");
					if (trTags != null && trTags.size() > 0) {

						monthWiseLinks = new ArrayList<>();
						for (Element trTag : trTags) {
							String date = ParserUtils.getElementByCSSClassName(trTag, "td", "date").get(0).text();
							Element messageLink = ParserUtils.getElementByCSSClassName(trTag, "td", "subject").get(0);

							if (messageLink != null) {
								Elements aTags = ParserUtils.getElement(messageLink, "a");
								if (aTags != null && aTags.size() > 0) {
									MailInfo mailInfo = new MailInfo();
									String mailBodyLink = ParserUtils.getAbsoluteHrefUri(aTags.get(0));

									mailInfo.setMailBodyLink(mailBodyLink);
									mailInfo.setDate(date);

									monthWiseLinks.add(mailInfo);
								}
							}
						}

					} else {
						throw new WebCrawlerException("No messages found for " + pageWiseMonthLink);
					}
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
