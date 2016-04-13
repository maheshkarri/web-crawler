package com.pramati.schedular.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.beans.MonthMailsInfo;
import com.pramati.utils.ParserUtils;

public class MonthMailsWorker implements Runnable {

	private MonthMailsInfo monthMailsInfo = null;

	private static final Logger logger = LoggerFactory.getLogger(MonthMailsWorker.class);

	public MonthMailsWorker(MonthMailsInfo monthMailsInfo) {
		this.monthMailsInfo = monthMailsInfo;
	}

	@Override
	public void run() {
		// logger.info(monthMailsInfo.toString());

		Document monthDocument = ParserUtils.getRootDocument(monthMailsInfo.getLink());

		if (monthDocument != null) {

			Element tableMsgList = ParserUtils.getElementByCSSIdAttribute(monthDocument, "table", "msglist").get(0);

			if (tableMsgList != null) {
				Element tbodyTag = ParserUtils.getElement(tableMsgList, "tbody").get(0);

				Elements trsTag = ParserUtils.getElement(tbodyTag, "tr");

				if (trsTag != null && trsTag.size() > 0) {

					List<MailInfo> mailInfos = new ArrayList<>();
					for (Element trTag : trsTag) {

						MailInfo mailInfo = new MailInfo();

						Element authorTag = ParserUtils.getElementByCSSClassName(trTag, "td", "author").get(0);
						if (authorTag != null) {
							mailInfo.setAuthor(ParserUtils.getElementData(authorTag));
						}

						Element subjectTag = ParserUtils.getElementByCSSClassName(trTag, "td", "subject").get(0);
						if (subjectTag != null) {

							Elements aTags = ParserUtils.getElement(subjectTag, "a");
							if (aTags != null && aTags.size() > 0) {
								Element aTag = aTags.get(0);
								if (aTag != null) {
									StringBuilder msgBodyLink = new StringBuilder();
									String mailBodyLink = ParserUtils.getAttributeValue(aTag, "href");
									
									msgBodyLink.append(monthMailsInfo.getLink().replace("thread", "ajax"));
									msgBodyLink.append("/");
									msgBodyLink.append(StringEscapeUtils.unescapeHtml4(mailBodyLink));
									
									
									mailInfo.setMailBodyLink(msgBodyLink.toString());
									
									Document rootDocument = ParserUtils.getRootDocument(mailInfo.getMailBodyLink());
									
									if(rootDocument != null){
									
										mailInfo.setMailBody(rootDocument.select("contents").first().text());
									
									}
									
									
								}
							}
							mailInfo.setSubject(ParserUtils.getElementData(subjectTag));
						}

						Element dateTag = ParserUtils.getElementByCSSClassName(trTag, "td", "date").get(0);
						if (dateTag != null) {
							mailInfo.setDate(ParserUtils.getElementData(dateTag));
						}

						mailInfos.add(mailInfo);
						
						logger.info(mailInfo.toString());

					}

				}

			}

		}
	}
}
