package com.bpedroso.challenge.contracts.controller;

import static java.lang.String.format;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageUserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private LocalDate timestamp;
	private ResponseContentUser responseContentUser;

	public MessageUserResponse() {
	}

	public MessageUserResponse(String uuid, LocalDate timestamp) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
	}

	public MessageUserResponse(String uuid, LocalDate timestamp, ResponseContentUser responseContentUser) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
		this.responseContentUser = responseContentUser;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public ResponseContentUser getResponseContentUser() {
		return responseContentUser;
	}

	public void setResponseContentUser(ResponseContentUser responseContentUser) {
		this.responseContentUser = responseContentUser;
	}

	@Override
	public String toString() {
		return format("MessageUserResponse [uuid=%s, timestamp=%s, responseContentUser=%s]", uuid, timestamp, responseContentUser);
	}

}
