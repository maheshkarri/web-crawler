package com.pramati.beans;

public class MonthMailsInfo {
	
	private String month;
	private String link;
	private int msgCount;
	
	public MonthMailsInfo() {
		// TODO Auto-generated constructor stub
	}

	public MonthMailsInfo(String month, String link, int msgCount) {
		super();
		this.month = month;
		this.link = link;
		this.msgCount = msgCount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	@Override
	public String toString() {
		return "MonthMailsInfo [month=" + month + ", link=" + link + ", msgCount=" + msgCount + "]";
	}
	
	
	
}
