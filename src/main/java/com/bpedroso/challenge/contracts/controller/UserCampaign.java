package com.bpedroso.challenge.contracts.controller;

import static java.lang.String.format;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCampaign {

	private String userEmail;

	private Integer campaignCode;

	public String getIdUser() {
		return userEmail;
	}

	public void setIdUser(String idUser) {
		this.userEmail = idUser;
	}

	public Integer getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(Integer campaignCode) {
		this.campaignCode = campaignCode;
	}

	@Override
	public String toString() {
		return format("UserCampaign [userEmail=%s, campaignCode=%s]", userEmail, campaignCode);
	}

}
