package com.pramati.webcrawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pramati.beans.MonthMailsInfo;
import com.pramati.constants.Parsers;
import com.pramati.factory.ParserFactory;
import com.pramati.schedular.MonthMailsSchedular;
import com.pramati.utils.ParserUtils;
import com.pramati.webcrawler.parser.MailParser;

public class WebCrawler {

	final String mailListLink = "http://mail-archives.apache.org/mod_mbox/maven-users/";

	final int requiredMailsOfYear = 2014;

	public static void main(String[] args) {
		new WebCrawler();
	}

	public Element getRequiredYearTable(Elements tables, int requiredYear) {
		Element resultTable = null;
		for (Element table : tables) {

			Element thead = ParserUtils.getElement(table, "thead").get(0);
			if (thead != null) {
				Element tr = ParserUtils.getElement(thead, "tr").get(0);
				if (tr != null) {
					Element th = ParserUtils.getElement(tr, "th").get(0);
					if (th != null) {
						if (th.text().contains(String.valueOf(requiredYear))) {
							resultTable = table;
							break;
						}
					}
				}
			}
		}
		return resultTable;
	}

	public List<MonthMailsInfo> getMonthWiseMailsInfo(Element table) {
		List<MonthMailsInfo> monthMailsInfos = null;
		if (table != null) {
			Element tbody = ParserUtils.getElement(table, "tbody").get(0);
			if (tbody != null) {
				Elements trs = ParserUtils.getElement(tbody, "tr");
				if (trs != null && trs.size() > 0) {

					monthMailsInfos = new ArrayList<>();

					for (Element tr : trs) {

						MonthMailsInfo monthMailsInfo = new MonthMailsInfo();

						Element date = ParserUtils.getElementByCSSClassName(tr, "td", "date").get(0);
						if (date != null) {
							monthMailsInfo.setMonth(ParserUtils.getElementData(date));
						}

						Element links = ParserUtils.getElementByCSSClassName(tr, "td", "links").get(0);
						if (links != null) {
							Element span = ParserUtils.getElement(links, "span").get(0);
							Element aTag = ParserUtils.getElement(span, "a").get(0);
							if (aTag != null) {
								monthMailsInfo.setLink(mailListLink + ParserUtils.getAttributeValue(aTag, "href"));
							}
						}

						Element msgCount = ParserUtils.getElementByCSSClassName(tr, "td", "msgCount").get(0);
						if (msgCount != null) {
							monthMailsInfo.setMsgCount(Integer.parseInt(msgCount.text()));
						}

						monthMailsInfos.add(monthMailsInfo);
					}
				}
			}
		}
		return monthMailsInfos;
	}

	public WebCrawler() {

		MailParser mailParser = (MailParser) ParserFactory.getFactory(Parsers.MAIL_PARSER.name());

		Document doc = ParserUtils.getRootDocument(mailListLink);

		if (doc != null) {

			Elements tables = ParserUtils.getElementByCSSClassName(doc, "table", "year");

			if (tables != null && tables.size() > 0) {
				Element table = getRequiredYearTable(tables, requiredMailsOfYear);

				List<MonthMailsInfo> monthMailsInfos = getMonthWiseMailsInfo(table);

				if (monthMailsInfos != null && !monthMailsInfos.isEmpty()) {

					 MonthMailsSchedular monthMailsSchedular = new MonthMailsSchedular(monthMailsInfos);
					 monthMailsSchedular.schedule();
						
				}
			}
		}

	}
}