package com.pramati.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.beans.MailInfo;
import com.pramati.dao.MailsStorageDAO;
import com.pramati.webcrawler.WebCrawler;

public class MailsStorageDAOImpl implements MailsStorageDAO {

	private static final Logger logger = LoggerFactory.getLogger(MailsStorageDAOImpl.class);

	private String getRootDirectory() {
		return "mails/";
	}

	@Override
	public boolean isMailDirectoryExists(String directoryName) {
		directoryName = getRootDirectory() + directoryName;

		File file = new File(directoryName);
		if (file != null && file.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean createMailDirectory(String directoryName) {
		directoryName = getRootDirectory() + directoryName;

		File file = new File(directoryName);
		return file.mkdirs();

	}

	@Override
	public int getMailsCount(String directoryName) {
		int count = 0;
		directoryName = getRootDirectory() + directoryName;

		File file = new File(directoryName);
		count = file.list().length;

		return count;
	}

	@Override
	public boolean isMailExistsInDirectory(String directoryName, String mailFileName) {
		directoryName = getRootDirectory() + directoryName;
		return new File(directoryName, mailFileName + ".txt").exists();
	}

	@Override
	public boolean createMailFile(String directoryName, MailInfo mailInfo) {
		boolean result = false;
		if (mailInfo.getDate() != null && !mailInfo.getDate().isEmpty()) {
			directoryName = getRootDirectory() + directoryName;
			File file = new File(directoryName + "/" + mailInfo.getDate() + ".txt");
			if (file != null) {
				try {

					if (file.createNewFile()) {
						FileWriter writer = new FileWriter(file);
						writer.write("From: " + mailInfo.getAuthor());
						writer.write("Subject: " + mailInfo.getSubject());
						writer.write("Date: " + mailInfo.getDate());
						writer.write("Body: " + mailInfo.getMailBody());
						writer.flush();
						writer.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
