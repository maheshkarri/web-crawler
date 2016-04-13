package com.pramati.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserUtils {

	private static final Logger logger = LoggerFactory.getLogger(ParserUtils.class);

	public static Document getRootDocument(String link) {

		try {
			return Jsoup.connect(link).get();
		} catch (IOException e) {
			logger.error("invalid link " + link);
			return null;
		}
	}

	public static Elements getElement(Element parent, String childElementName) {
		return parent.select(childElementName);
	}

	public static Elements getElementByCSSClassName(Element parent, String childElementName, String cssClassName) {
		return parent.select(childElementName + "[class=" + cssClassName + "]");
	}

	public static Elements getElementByCSSIdAttribute(Element parent, String childElementName, String cssIdAttribute) {
		return parent.select(childElementName + "[id=" + cssIdAttribute + "]");
	}
	
	public static String getElementData(Element element){
		return element.text();
	}
	
	public static String getAttributeValue(Element element , String attributeName){
		return element.attr(attributeName);
	}
}
