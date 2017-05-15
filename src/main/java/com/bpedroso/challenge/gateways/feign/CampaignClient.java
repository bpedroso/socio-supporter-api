package com.bpedroso.challenge.gateways.feign;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;

@FeignClient(name = "${feign.campaignapi.name}", url = "${feign.campaignapi.url:}", fallback = CampaignFallback.class)
public interface CampaignClient {

	@RequestMapping(method = GET, value = "${feign.campaignapi.endpoints.campaign}", produces = APPLICATION_JSON_UTF8_VALUE)
	MessageContextCampaign getCampaigns(
			@RequestHeader(value = "messageId") String messageId,
			@RequestParam(value = "code", required = false) Integer code,
			@RequestParam(value = "idTeam", required = false) Integer idTeam);

	@RequestMapping(method = DELETE, value = "${feign.campaignapi.endpoints.campaign}", produces = APPLICATION_JSON_UTF8_VALUE)
	MessageContextCampaign deleteCampaign(
			@RequestHeader(value = "messageId") String messageId,
			@RequestParam(value = "code", required = false) int code);

	@RequestMapping(method = POST, value = "${feign.campaignapi.endpoints.campaign}", produces = APPLICATION_JSON_UTF8_VALUE)
	MessageContextCampaign createCampaign(
			@RequestHeader(value = "messageId") String messageId,
			@RequestBody Campaign campaign);

}
