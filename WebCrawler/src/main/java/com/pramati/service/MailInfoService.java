package com.pramati.service;

import java.util.List;

import org.jsoup.nodes.Element;

import com.pramati.beans.MailInfo;
import com.pramati.exception.WebCrawlerException;

public interface MailInfoService {
	public List<String> getMonthLinks(String link, int requiredYear) throws WebCrawlerException;

	public List<String> getPageWiseMonthLink(String monthLink) throws WebCrawlerException;
	
	public List<String> getMonthWiseLinks(String pageWiseMonthLink) throws WebCrawlerException;
	
	public MailInfo getMailInfo(String mailLink) throws WebCrawlerException;
}
