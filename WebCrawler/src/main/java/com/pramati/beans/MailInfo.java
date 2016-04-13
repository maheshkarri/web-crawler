package com.pramati.beans;

public class MailInfo {
	private String author;
	private String subject;
	private String date;
	private String mailBodyLink;
	private String mailBody;
	
	public MailInfo() {
		// TODO Auto-generated constructor stub
	}

	public MailInfo(String author, String subject, String date, String mailBodyLink, String mailBody) {
		super();
		this.author = author;
		this.subject = subject;
		this.date = date;
		this.mailBodyLink = mailBodyLink;
		this.mailBody = mailBody;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMailBodyLink() {
		return mailBodyLink;
	}

	public void setMailBodyLink(String mailBodyLink) {
		this.mailBodyLink = mailBodyLink;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	@Override
	public String toString() {
		return "MailInfo [author=" + author + ", subject=" + subject + ", date=" + date + ", mailBodyLink="
				+ mailBodyLink + ", mailBody=" + mailBody + "]";
	}
	
	
}
