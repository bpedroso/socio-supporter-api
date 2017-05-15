package com.bpedroso.challenge.contracts.controller;

import static java.lang.String.format;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private LocalDate timestamp;
	private Object message;
	private String responseError;

	public MessageContext() {
	}

	public MessageContext(String uuid, LocalDate timestamp) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
	}
	
	public MessageContext(String uuid, LocalDate timestamp, String responseError) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
		this.responseError = responseError;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getError() {
		return responseError;
	}
	
	public void setError(String responseError) {
		this.responseError = responseError;
	}

	public Object getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return format("MessageContext [uuid=%s, instant=%s, message=%s, responseError=%s]", uuid, 
				timestamp, message, responseError);
	}

}