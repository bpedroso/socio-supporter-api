package com.bpedroso.challenge.gateways.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class UserFallbackFactory implements FallbackFactory<UserClient> {

	private final Logger LOGGER = LoggerFactory.getLogger(UserFallbackFactory.class);

	@Autowired
	private UserFallback userFallback;

	@Override
	public UserClient create(final Throwable cause) {
		LOGGER.error("Original exception", cause);
	    return userFallback;
	}

}
