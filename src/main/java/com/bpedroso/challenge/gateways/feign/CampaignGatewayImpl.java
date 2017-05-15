package com.bpedroso.challenge.gateways.feign;

import static java.util.Optional.ofNullable;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;
import com.bpedroso.challenge.gateways.CampaignGateway;

@Component
public class CampaignGatewayImpl implements CampaignGateway {

	private CampaignClient campaignClient;

	@Autowired
	public CampaignGatewayImpl(CampaignClient campaignClient) {
		this.campaignClient = campaignClient;
	}

	@Override
	public Optional<MessageContextCampaign> getCampaigns(String messageId, Integer code) {
		return ofNullable(this.campaignClient.getCampaigns(messageId, code, null));
	}
	
	@Override
	public Optional<MessageContextCampaign> getCampaignsByTeam(String messageId, Integer idTeam) {
		return ofNullable(this.campaignClient.getCampaigns(messageId, null, idTeam));
	}

	@Override
	public Optional<MessageContextCampaign> deleteCampaigns(String messageId, Integer code) {
		return ofNullable(this.campaignClient.deleteCampaign(messageId, code));
	}

	@Override
	public Optional<MessageContextCampaign> updateCampaigns(String messageId, Campaign campaign) {
		return ofNullable(this.campaignClient.createCampaign(messageId, campaign));
	}

}