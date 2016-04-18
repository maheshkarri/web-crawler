package com.pramati.schedular;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.schedular.worker.MonthMailsWorker;
import com.pramati.service.MailInfoService;

public class MonthMailsSchedular {

	final int fixedThreadPoolSize = 20;

	ThreadPoolExecutor executor = null;

	MailInfoService mailInfoService = null;

	private static final Logger logger = LoggerFactory.getLogger(MonthMailsSchedular.class);

	private MonthMailsSchedular() {
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(fixedThreadPoolSize);
	}

	private static volatile MonthMailsSchedular schedular = null;

	public static MonthMailsSchedular getInstance() {
		if (schedular == null) {
			synchronized (MonthMailsSchedular.class) {
				if (schedular == null) {
					schedular = new MonthMailsSchedular();
				}
			}
		}
		return schedular;
	}

	public MailInfo schedule(String mailLink) {

		MailInfo mailInfo = null;

		try {
			if (executor != null) {
				MonthMailsWorker workerThread = new MonthMailsWorker(mailLink);
				Future<MailInfo> future = executor.submit(workerThread);

				mailInfo = future.get();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mailInfo;

	}

	public void shutdown() {
		if (executor != null) {
			executor.shutdown();
		}
	}
}
