package com.bpedroso.challenge.contracts.controller;

import static java.lang.String.format;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageTeamResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String uuid;
	private LocalDate timestamp;
	private Object entity;
	private String responseMessage;

	public MessageTeamResponse() {
	}

	public MessageTeamResponse(String uuid, LocalDate timestamp) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
	}
	
	public MessageTeamResponse(String uuid, LocalDate timestamp, String responseMessage) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
		this.responseMessage = responseMessage;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
	
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Object getEntity() {
		return entity;
	}

	@Override
	public String toString() {
		return format("MessageContext [uuid=%s, instant=%s, message=%s, responseMessager=%s]", uuid, 
				timestamp, entity, responseMessage);
	}

}