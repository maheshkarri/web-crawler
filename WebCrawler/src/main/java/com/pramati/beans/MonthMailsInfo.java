package com.pramati.beans;

public class MonthMailsInfo {
	private String month;
	private String monthLink;
	private int msgCount;
	
	public MonthMailsInfo() {
		// TODO Auto-generated constructor stub
	}

	public MonthMailsInfo(String month, String monthLink, int msgCount) {
		super();
		this.month = month;
		this.monthLink = monthLink;
		this.msgCount = msgCount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonthLink() {
		return monthLink;
	}

	public void setMonthLink(String monthLink) {
		this.monthLink = monthLink;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	@Override
	public String toString() {
		return "MonthMailsInfo [month=" + month + ", monthLink=" + monthLink + ", msgCount=" + msgCount + "]";
	}
	
	
}
