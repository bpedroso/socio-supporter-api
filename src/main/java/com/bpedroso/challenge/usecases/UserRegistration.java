package com.bpedroso.challenge.usecases;

import static com.bpedroso.challenge.usecases.constants.MessageResponse.CAMPAIGN_NOT_EXISTS;
import static com.bpedroso.challenge.usecases.constants.MessageResponse.USER_ALREADY_EXISTS;
import static com.bpedroso.challenge.usecases.constants.MessageResponse.USER_NOT_EXISTS;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextUser;
import com.bpedroso.challenge.contracts.controller.MessageUserResponse;
import com.bpedroso.challenge.contracts.controller.ResponseContentUser;
import com.bpedroso.challenge.contracts.controller.User;
import com.bpedroso.challenge.gateways.CampaignGateway;
import com.bpedroso.challenge.gateways.UserGateway;

/*
 * Eu, como usuário, quero me cadastrar informando meu e-mail e o meu Time
 * do Coração através de uma API que me permite participar de algumas
 * campanhas. Para tanto, os critérios de aceite dessa história são:
 * 
 * Nome Completo; E-mail; Data de Nascimento; Meu Time do Coração;
 */
@Component
public class UserRegistration {

	private final Logger log = LoggerFactory.getLogger(UserRegistration.class);

	private CampaignGateway campaignGateway;

	private UserGateway userGateway;

	@Autowired
	public UserRegistration(CampaignGateway productGateway, UserGateway userRepository) {
		this.campaignGateway = productGateway;
		this.userGateway = userRepository;
	}

	/*
	 * 1 - dado um e-mail que já existe, informar que o cadastro já foi efetuado
	 * 2 - caso o cliente não tenha nenhuma campanha associada, o serviço deverá
	 * enviar as novas campanhas como resposta
	 */
	public MessageUserResponse register(String messageId, User payLoad) {
		final MessageUserResponse mr = new MessageUserResponse(messageId, now());
		final ResponseContentUser responseContentUser = new ResponseContentUser();

		if (log.isDebugEnabled()) {
			log.debug("Starting save user process {}", payLoad);
		}

		final Optional<MessageContextUser> optUser = this.userGateway
				.findUserByEmail(messageId, payLoad.getEmail());

		if (optUser.isPresent()) {
			log.info("User already exists {}", payLoad);
			responseContentUser.setResponseMessage(USER_ALREADY_EXISTS);
			userHasCampaignAssociated(messageId, optUser.get().getUser(), responseContentUser);
		} else {
			this.userGateway.save(messageId, payLoad);
		}
		mr.setResponseContentUser(responseContentUser);
		return mr;
	}

	public MessageUserResponse addCampaigns(String messageId, String userEmail, Integer campaignCode) {
		final MessageUserResponse mr = new MessageUserResponse(messageId, now());
		final ResponseContentUser responseContent = new ResponseContentUser();

		if (log.isDebugEnabled()) {
			log.debug("Starting add user {} campaign {}", userEmail, campaignCode);
		}

		final Optional<MessageContextUser> optUser = this.userGateway.findUserByEmail(messageId, userEmail);

		if (optUser.isPresent()) {
			final Optional<MessageContextCampaign> messageContext = 
					this.campaignGateway.getCampaigns(messageId, campaignCode);

			if (messageContext.isPresent()) {
				MessageContextUser mUser = optUser.get();
				User user = mUser.getUser();
				user.setCampagns(messageContext.get().getCampaigns());
				this.userGateway.save(messageId, user);
			} else {
				responseContent.setResponseMessage(format(CAMPAIGN_NOT_EXISTS, campaignCode));
			}
		} else {
			responseContent.setResponseMessage(format(USER_NOT_EXISTS, userEmail));
		}

		mr.setResponseContentUser(responseContent);
		return mr;
	}

	// Estou mostrando somente as campanhas do time do usuario
	private void userHasCampaignAssociated(String messageId, User user, ResponseContentUser mcu) {
		if (isEmpty(user.getCampagns())) {
			mcu.setCampaigns(this.campaignGateway.getCampaignsByTeam(messageId, user.getIdTeam())
					.orElse(new MessageContextCampaign()).getCampaigns());
		}
	}

}
