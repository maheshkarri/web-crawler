package com.pramati.schedular;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.pramati.schedular.worker.MonthMailsWorker;
import com.pramati.service.MailInfoService;

public class MonthMailsSchedular {

	List<String> mailLinks = null;

	final int fixedThreadPoolSize = 10;

	ThreadPoolExecutor executor = null;

	MailInfoService mailInfoService = null;

	public MonthMailsSchedular(List<String> mailLinks) {
		this.mailLinks = mailLinks;

		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(fixedThreadPoolSize);

	}

	public void schedule() {
		for (String mailLink : mailLinks) {
			MonthMailsWorker workerThread = new MonthMailsWorker(mailLink);

			executor.submit(workerThread);
		}

	}
}
