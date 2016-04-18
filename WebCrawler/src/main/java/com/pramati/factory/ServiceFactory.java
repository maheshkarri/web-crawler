package com.pramati.factory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
private static Map<String , Object> servicePool = new HashMap<>();
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
	
	
	public static Object getService(String serviceName){
		
		Object service = null;
		
		
		try{
		
		if(serviceName != null && !serviceName.isEmpty() && com.pramati.constants.DataServiceTypes.valueOf(serviceName) != null){
			if(!servicePool.containsKey(serviceName)){
				synchronized (ServiceFactory.class) {
					if(!servicePool.containsKey(serviceName)){
						service = Class.forName(com.pramati.constants.DataServiceTypes.valueOf(serviceName).getAbbrevation()).newInstance();
						servicePool.put(serviceName, service);
					}
				}
			}else{
				service = servicePool.get(serviceName);
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Invalid serviceName "+serviceName);
		}
		
		return service;
		
	}
}
