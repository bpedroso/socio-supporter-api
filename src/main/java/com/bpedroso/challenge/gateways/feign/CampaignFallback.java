package com.bpedroso.challenge.gateways.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;

@Component
public class CampaignFallback implements CampaignClient {
	
	private final Logger LOGGER = LoggerFactory.getLogger(CampaignFallback.class);

	@Override
	public MessageContextCampaign getCampaigns(String messageId, Integer code, Integer idTeam) {
		LOGGER.error("Fail to get campaign [messageId={}] [code={}] [idTeam={}]", messageId, code, idTeam);
		return null;
	}

	@Override
	public MessageContextCampaign deleteCampaign(String messageId, int code) {
		LOGGER.error("Fail to delete campaign [messageId={}] [code={}]", messageId, code);
		return null;
	}

	@Override
	public MessageContextCampaign createCampaign(String messageId, Campaign campaign) {
		LOGGER.error("Fail to create or update campaign [messageId={}] - {} [code={}]", messageId, campaign);
		return null;
	}

}