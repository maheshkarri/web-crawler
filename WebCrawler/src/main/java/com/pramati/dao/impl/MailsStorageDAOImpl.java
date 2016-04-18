package com.pramati.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

import com.pramati.beans.MailInfo;
import com.pramati.dao.MailsStorageDAO;

public class MailsStorageDAOImpl implements MailsStorageDAO {

	@Override
	public boolean isMailDirectoryExists(String directoryName) {
		File file = new File(directoryName);
		if(file != null && file.isDirectory()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean createMailDirectory(String directoryName) {
		if(!isMailDirectoryExists(directoryName)){
			File file = new File(directoryName);
			return file.mkdirs();
		}else{
			return false;
		}
	}

	@Override
	public int getMailsCount(String directoryName) {
		int count = 0;
		if(isMailDirectoryExists(directoryName)){
			File file = new File(directoryName);
			count = file.list().length;
		}
		return count;
	}

	@Override
	public boolean isMailExistsInDirectory(String directoryName, String mailFileName) {
		return new File(directoryName , mailFileName+".txt").exists();
	}

	@Override
	public boolean createMailFile(String directoryName, MailInfo mailInfo) {
		boolean result = false;
		File file = new File(directoryName+"/"+mailInfo.getDate()+".txt");
		if(file != null){
			try {
				System.out.println(file.getAbsolutePath());
				if(file.createNewFile()){
					FileWriter writer = new FileWriter(file);
					writer.write("From: "+mailInfo.getAuthor());
					writer.write("Subject: "+mailInfo.getSubject());
					writer.write("Date: "+mailInfo.getDate());
					writer.write("Body: "+mailInfo.getMailBody());
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
