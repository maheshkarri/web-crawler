package com.pramati.dao;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.pramati.beans.MailInfo;
import com.pramati.beans.MonthMailsInfo;
import com.pramati.exception.WebCrawlerException;

public interface MailInfoDAO {
	public List<MonthMailsInfo> fetchMonthLinks(String link , int requiredYear) throws WebCrawlerException;
	
	public Map<Integer , String> getPageWiseMonthLink(String monthLink) throws WebCrawlerException;
	
	public List<MailInfo> fetchMonthWiseLinks(String pageWiseMonthLink) throws WebCrawlerException;
	
	public MailInfo fetchMailInfo(String mailLink) throws WebCrawlerException;
	
}
