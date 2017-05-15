package com.bpedroso.challenge.gateways.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class CampaignFallbackFactory implements FallbackFactory<CampaignClient> {

	private final Logger LOGGER = LoggerFactory.getLogger(CampaignFallbackFactory.class);

	@Autowired
	private CampaignFallback campaignFallback;

	@Override
	public CampaignClient create(final Throwable cause) {
		LOGGER.error("Original exception", cause);
	    return campaignFallback;
	}

}
