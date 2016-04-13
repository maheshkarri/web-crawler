package com.pramati.schedular;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.pramati.beans.MonthMailsInfo;
import com.pramati.schedular.worker.MonthMailsWorker;

public class MonthMailsSchedular {
	
	List<MonthMailsInfo> monthMailsInfos = null;
	
	final int fixedThreadPoolSize = 3;
	
	ThreadPoolExecutor executor = null;
	
	public MonthMailsSchedular(List<MonthMailsInfo> monthMailsInfos) {
		this.monthMailsInfos = monthMailsInfos;
		
		executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(fixedThreadPoolSize);
	}
	
	public void schedule(){
		for (MonthMailsInfo monthMailsInfo : monthMailsInfos) {
			MonthMailsWorker workerThread = new MonthMailsWorker(monthMailsInfo);
			
			executor.execute(workerThread);
		}
		
		
	}
}
