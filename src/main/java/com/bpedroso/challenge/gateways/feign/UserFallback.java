package com.bpedroso.challenge.gateways.feign;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.bpedroso.challenge.contracts.campaignapi.MessageContextUser;
import com.bpedroso.challenge.contracts.controller.User;

@Component
public class UserFallback implements UserClient {

	private final Logger LOGGER = getLogger(UserFallback.class);

	@Override
	public MessageContextUser getUsers(String messageId, String email) {
		String fallbackMessage = String.format("Fail to get users [messageId=%s] [email=%s]", messageId, email);
		LOGGER.error(fallbackMessage);
		return null;
	}

	@Override
	public MessageContextUser createUser(String messageId, User user) {
		String fallbackMessage = String.format("Fail to create a user[messageId={}] - {} [user={}]", messageId, user.toString());
		LOGGER.error(fallbackMessage);
		return null;
	}

}
