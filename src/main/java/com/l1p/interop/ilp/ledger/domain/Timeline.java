package com.l1p.interop.ilp.ledger.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Timeline {

	@JsonIgnore
	@JsonProperty(value = "proposed_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date proposedAt;

	@JsonProperty(value = "prepared_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date preparedAt;

	@JsonProperty(value = "executed_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date executedAt;

	@JsonProperty(value = "rejected_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date rejectedAt;

	public Timeline() {
	}

	public Date getProposedAt() {
		return proposedAt;
	}

	public void setProposedAt(Date proposedAt) {
		this.proposedAt = proposedAt;
	}

	public Date getPreparedAt() {
		return preparedAt;
	}

	public void setPreparedAt(Date preparedAt) {
		this.preparedAt = preparedAt;
	}

	public Date getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(Date executedAt) {
		this.executedAt = executedAt;
	}

	public Date getRejectedAt() {
		return rejectedAt;
	}

	public void setRejectedAt(Date rejectedAt) {
		this.rejectedAt = rejectedAt;
	}
}
