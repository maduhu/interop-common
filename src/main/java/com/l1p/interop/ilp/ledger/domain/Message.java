package com.l1p.interop.ilp.ledger.domain;

public class Message {
	
	private String id;
	private String ilp;
	private String ledger;
	private String from;
	private String to;
	private Object data;

	public String getLedger() {
		return ledger;
	}

	public void setLedger(String ledger) {
		this.ledger = ledger;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ilp
	 */
	public String getIlp() {
		return ilp;
	}

	/**
	 * @param ilp the ilp to set
	 */
	public void setIlp(String ilp) {
		this.ilp = ilp;
	}

}
