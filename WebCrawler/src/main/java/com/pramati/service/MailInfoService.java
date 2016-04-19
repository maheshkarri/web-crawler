package com.pramati.service;

import java.util.List;
import java.util.Map;

import com.pramati.beans.MailInfo;
import com.pramati.beans.MonthMailsInfo;
import com.pramati.exception.WebCrawlerException;

public interface MailInfoService {
	public List<MonthMailsInfo> getMonthLinks(String link, int requiredYear) throws WebCrawlerException;

	public Map<Integer , String> getPageWiseMonthLink(String monthLink) throws WebCrawlerException;
	
	public List<MailInfo> getMonthWiseLinks(String pageWiseMonthLink) throws WebCrawlerException;
	
	public MailInfo getMailInfo(String mailLink) throws WebCrawlerException;
}
