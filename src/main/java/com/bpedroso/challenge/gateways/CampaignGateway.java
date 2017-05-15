package com.bpedroso.challenge.gateways;

import java.util.Optional;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;

public interface CampaignGateway {

	Optional<MessageContextCampaign> getCampaigns(String messageId, int code);
	
	Optional<MessageContextCampaign> getCampaignsByTeam(String messageId, int idTeam);
	
	Optional<MessageContextCampaign> deleteCampaigns(String messageId, int code);
	
	Optional<MessageContextCampaign> updateCampaigns(String messageId, Campaign campaign);

}
