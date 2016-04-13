package com.pramati.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCrawlerException extends Exception {

	private static final Logger logger = LoggerFactory.getLogger(WebCrawlerException.class);
	
	
	public WebCrawlerException(String message) {
		
		super(message);
		logger.error(message);
	}
	
}
