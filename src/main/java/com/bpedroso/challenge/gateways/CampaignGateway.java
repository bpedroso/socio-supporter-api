package com.bpedroso.challenge.gateways;

import java.util.Optional;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;

public interface CampaignGateway {

	Optional<MessageContextCampaign> getCampaigns(String messageId, Integer code);
	
	Optional<MessageContextCampaign> getCampaignsByTeam(String messageId, Integer idTeam);
	
	Optional<MessageContextCampaign> deleteCampaigns(String messageId, Integer code);
	
	Optional<MessageContextCampaign> updateCampaigns(String messageId, Campaign campaign);

}
