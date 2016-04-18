package com.pramati.dao;

import com.pramati.beans.MailInfo;

public interface MailsStorageDAO {
	public boolean isMailDirectoryExists(String directoryName);
	public boolean createMailDirectory(String directoryName);
	public int getMailsCount(String directoryName);
	public boolean isMailExistsInDirectory(String directoryName , String mailFileName);
	public boolean createMailFile(String directoryName , MailInfo mailInfo);
}
