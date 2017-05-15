package com.bpedroso.challenge.contracts.controller;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.List;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseContentUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String responseMessage;
	private List<Campaign> campaigns;

	public ResponseContentUser() {
		super();
	}

	public ResponseContentUser(String responseMessage) {
		super();
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	@Override
	public String toString() {
		return format("ResponseContentUser [responseMessage=%s, campaigns=%s]", responseMessage, campaigns);
	}

}
