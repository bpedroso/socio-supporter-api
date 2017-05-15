package com.bpedroso.challenge.gateways.feign;

import static java.util.Optional.ofNullable;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bpedroso.challenge.contracts.campaignapi.MessageContextUser;
import com.bpedroso.challenge.contracts.controller.User;
import com.bpedroso.challenge.gateways.UserGateway;

@Component
public class UserGatewayImpl implements UserGateway {

	private UserClient userClient;

	@Autowired
	public UserGatewayImpl(UserClient userClient) {
		this.userClient = userClient;
	}

	@Override
	public Optional<MessageContextUser> findUserByEmail(String messageId, String email) {
		return ofNullable(this.userClient.getUsers(messageId, email));
	}

	@Override
	public Optional<MessageContextUser> save(String messageId, User payLoad) {
		return ofNullable(this.userClient.createUser(messageId, payLoad));
	}

}