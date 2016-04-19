package com.pramati.service.impl;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pramati.beans.MailInfo;

public class MailsStorageServiceImplTest {

	private static MailsStorageServiceImpl mailsStorageServiceImpl = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mailsStorageServiceImpl = new MailsStorageServiceImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		mailsStorageServiceImpl = null;
		File file = new File("mails/sample");
		if(file.exists()){
			System.out.println("file exists");
			
			file.delete();
			
			file = new File("mails");
			
			file.delete();	
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateMailDirectory() {
		if (!mailsStorageServiceImpl.isMailDirectoryExists("sample")) {
			boolean createMailDirectory = mailsStorageServiceImpl.createMailDirectory("sample");
			if (!createMailDirectory) {
				fail("directory not created");
			}
		}

	}

	

	@Test
	public void testCreateMailFile() {

		boolean mailExistsInDirectory = mailsStorageServiceImpl.isMailExistsInDirectory("sample", "sample");

		if (!mailExistsInDirectory) {
			MailInfo mailInfo = new MailInfo();

			boolean createMailFile = mailsStorageServiceImpl.createMailFile("sample", mailInfo);

			assertFalse(createMailFile);
		}

	}

}
