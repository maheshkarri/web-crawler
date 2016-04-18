package com.pramati.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pramati.beans.MailInfo;
import com.pramati.beans.MonthMailsInfo;
import com.pramati.exception.WebCrawlerException;
import com.pramati.service.impl.MailInfoServiceImpl;

public class MailInfoServiceImplTest {

	private static MailInfoServiceImpl mailInfoServiceImpl = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mailInfoServiceImpl = new MailInfoServiceImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMonthLinks() {

		List<MonthMailsInfo> monthLinks;
		try {
			monthLinks = mailInfoServiceImpl
					.getMonthLinks(com.pramati.constants.WebCrawlerConstants.MAIL_LINK.getAbbrevation(), 2014);
			assertNotNull(monthLinks);
			
			assertNotSame(0, monthLinks.size());
			

		} catch (WebCrawlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testGetMonthWiseLinks() {

		try {
			List<MailInfo> monthWiseLinks = mailInfoServiceImpl
					.getMonthWiseLinks("http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/thread");
			assertNotNull(monthWiseLinks);

			assertNotEquals(0, monthWiseLinks.size());
		} catch (WebCrawlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test(timeout = 2000)
	public void testGetMailInfo() {

		MailInfo mailInfo;

		try {
			mailInfo = mailInfoServiceImpl.getMailInfo(
					"http://mail-archives.apache.org/mod_mbox/maven-users/201407.mbox/%3C1405852878849.27fc2e48@Nodemailer%3E");
			assertNotNull(mailInfo);
		} catch (WebCrawlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
