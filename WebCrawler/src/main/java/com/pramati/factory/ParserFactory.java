package com.pramati.factory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ParserFactory {

	private static Map<String , Object> factoryPool = new HashMap<>();
	
	private static final Logger logger = LoggerFactory.getLogger(ParserFactory.class);
	
	
	public static Object getFactory(String factoryName){
		
		Object factory = null;
		
		logger.info("request came "+factoryName);
		
		try{
		
		if(factoryName != null && !factoryName.isEmpty() && com.pramati.constants.Parsers.valueOf(factoryName) != null){
			if(!factoryPool.containsKey(factoryName)){
				synchronized (ParserFactory.class) {
					if(!factoryPool.containsKey(factoryName)){
						factory = Class.forName(com.pramati.constants.Parsers.valueOf(factoryName).getAbbrevation()).newInstance();
						factoryPool.put(factoryName, factory);
					}
				}
			}else{
				factory = factoryPool.get(factoryName);
			}
		}
		
		}catch(Exception e){
			logger.error("Invalid factoryName "+factoryName);
		}
		
		return factory;
		
		
	}
}
