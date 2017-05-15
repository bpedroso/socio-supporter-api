package com.bpedroso.challenge.contracts.campaignapi;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageContextCampaign implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private Instant instant;
	private List<Campaign> campaigns;
	private String responseError;

	public MessageContextCampaign() {
	}

	public MessageContextCampaign(String uuid, Instant instant) {
		super();
		this.uuid = uuid;
		this.instant = instant;
	}
	
	public MessageContextCampaign(String uuid, Instant instant, String responseError) {
		super();
		this.uuid = uuid;
		this.instant = instant;
		this.responseError = responseError;
	}
	
	public MessageContextCampaign(String uuid, Instant instant, List<Campaign> campaigns) {
		super();
		this.uuid = uuid;
		this.instant = instant;
		this.campaigns = campaigns;
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

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	@Override
	public String toString() {
		return String.format("MessageContext [uuid=%s, instant=%s, campaigns=%s, responseError=%s]", uuid, 
				instant, campaigns, responseError);
	}

}