package com.bpedroso.challenge.gateways;

import java.util.Optional;

import com.bpedroso.challenge.contracts.campaignapi.MessageContextUser;
import com.bpedroso.challenge.contracts.controller.User;

public interface UserGateway {

	Optional<MessageContextUser> findUserByEmail(String messageId, String email);

	Optional<MessageContextUser> save(String messageId, User payLoad);

}
