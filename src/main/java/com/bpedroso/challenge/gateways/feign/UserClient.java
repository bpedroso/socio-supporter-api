package com.bpedroso.challenge.gateways.feign;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpedroso.challenge.contracts.campaignapi.MessageContextUser;
import com.bpedroso.challenge.contracts.controller.User;

@FeignClient(name = "${feign.campaignapi.name}", url = "${feign.campaignapi.url:}", fallback = CampaignFallback.class)
public interface UserClient {

	@RequestMapping(method = GET, value = "${feign.campaignapi.endpoints.user}", produces = APPLICATION_JSON_UTF8_VALUE)
	MessageContextUser getUsers(
			@RequestHeader(value = "messageId") String messageId,
			@RequestParam(value = "email", required = false) String email);

	@RequestMapping(method = POST, value = "${feign.campaignapi.endpoints.user}", produces = APPLICATION_JSON_UTF8_VALUE)
	MessageContextUser createUser(
			@RequestHeader(value = "messageId") String messageId,
			@RequestBody User user);

}
