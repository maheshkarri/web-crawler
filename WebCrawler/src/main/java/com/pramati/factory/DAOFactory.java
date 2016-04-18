package com.pramati.factory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {
private static Map<String , Object> daoPool = new HashMap<>();
	
	private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);
	
	public static Object getDAO(String daoName){
		
		Object dao = null;
		
		try{
		
		if(daoName != null && !daoName.isEmpty() && com.pramati.constants.DAOTypes.valueOf(daoName) != null){
			if(!daoPool.containsKey(daoName)){
				synchronized (DAOFactory.class) {
					if(!daoPool.containsKey(daoName)){
						dao = Class.forName(com.pramati.constants.DAOTypes.valueOf(daoName).getAbbrevation()).newInstance();
						daoPool.put(daoName, dao);
					}
				}
			}else{
				dao = daoPool.get(daoName);
			}
		}
		
		}catch(Exception e){
			logger.error("Invalid daoName "+daoName);
		}
		
		return dao;
		
	}

}
