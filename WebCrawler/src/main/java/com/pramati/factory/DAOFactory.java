package com.pramati.factory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {
private static Map<String , Object> servicePool = new HashMap<>();
	
	private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);
	
	
	public static Object getDAO(String daoName){
		
		
		Object dao = null;
		
		logger.info("request came "+daoName);
		
		
		try{
		
		if(daoName != null && !daoName.isEmpty() && com.pramati.constants.DAOTypes.valueOf(daoName) != null){
			if(!servicePool.containsKey(daoName)){
				synchronized (DAOFactory.class) {
					if(!servicePool.containsKey(daoName)){
						dao = Class.forName(com.pramati.constants.DAOTypes.valueOf(daoName).getAbbrevation()).newInstance();
						servicePool.put(daoName, dao);
					}
				}
			}else{
				dao = servicePool.get(daoName);
			}
		}
		
		}catch(Exception e){
			logger.error("Invalid daoName "+daoName);
		}
		
		return dao;
		
	}

}
