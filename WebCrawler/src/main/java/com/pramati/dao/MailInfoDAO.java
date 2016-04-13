package com.pramati.dao;

import java.util.List;

import org.jsoup.nodes.Element;

import com.pramati.beans.MailInfo;
import com.pramati.exception.WebCrawlerException;

public interface MailInfoDAO {
	public List<String> fetchMonthLinks(String link , int requiredYear) throws WebCrawlerException;
	
	public List<String> fetchMonthWiseLinks(String monthLink) throws WebCrawlerException;
	
	public MailInfo fetchMailInfo(String mailLink) throws WebCrawlerException;
	
}
