package com.pramati.schedular;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.schedular.worker.MonthMailsWorker;
import com.pramati.service.MailInfoService;

public class MonthMailsSchedular {

	List<String> mailLinks = null;

	final int fixedThreadPoolSize = 20;

	ThreadPoolExecutor executor = null;

	MailInfoService mailInfoService = null;
	
	private static final Logger logger = LoggerFactory.getLogger(MonthMailsSchedular.class);

	public MonthMailsSchedular(List<String> mailLinks) {
		this.mailLinks = mailLinks;

		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(fixedThreadPoolSize);

	}

	public void schedule() {
		
		
		
		for (String mailLink : mailLinks) {
			MonthMailsWorker workerThread = new MonthMailsWorker(mailLink);

			Future future = executor.submit(workerThread);
			
			try {
				logger.info(future.get().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
