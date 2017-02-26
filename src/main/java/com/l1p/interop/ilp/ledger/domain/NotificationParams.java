package com.l1p.interop.ilp.ledger.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

abstract public class NotificationParams {
	private String id;
	private Object resource;
	private String event;
	@JsonProperty(value = "related_resources")
	private HashMap<String, String> relatedResources;
	
	public NotificationParams(String event, Object resource) {
		this.resource = resource;
		this.event = event;
	}

	public String getEvent() {
		return event;
	};

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public Map<String, String> getRelatedResources() {
		return relatedResources;
	}

	public void setRelatedResources(HashMap<String, String> relatedResources) {
		this.relatedResources = relatedResources;
	}
}
