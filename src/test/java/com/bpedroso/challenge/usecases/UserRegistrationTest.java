package com.bpedroso.challenge.usecases;

import static com.bpedroso.challenge.usecases.constants.MessageResponse.CAMPAIGN_NOT_EXISTS;
import static com.bpedroso.challenge.usecases.constants.MessageResponse.USER_ALREADY_EXISTS;
import static com.bpedroso.challenge.usecases.constants.MessageResponse.USER_NOT_EXISTS;
import static java.lang.String.format;
import static java.time.Instant.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextUser;
import com.bpedroso.challenge.contracts.controller.MessageUserResponse;
import com.bpedroso.challenge.contracts.controller.User;
import com.bpedroso.challenge.gateways.CampaignGateway;
import com.bpedroso.challenge.gateways.UserGateway;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {
	
	private static final int ANY_CAMPAIGN_CODE = 1;
	private static final String ANY_MESSAGE_ID = UUID.randomUUID().toString();
	private static final String ANY_USER_EMAIL = "email@email.com";
	
	private static final MessageContextUser MESSAGE_CONTEXT_NOT_PRESENT = null;

	@Mock
	private CampaignGateway campaignGateway;

	@Mock
	private UserGateway userGateway;
	
	@InjectMocks
	public UserRegistration userRegistration;

	@Test
	public void register_userDoenstExits_callSaveMethodOnGateway() {
		User user = new User();
		user.setEmail(ANY_USER_EMAIL);
		
		when(userGateway.findUserByEmail(anyString(), anyString()))
			.thenReturn(Optional.ofNullable(MESSAGE_CONTEXT_NOT_PRESENT));

		MessageUserResponse actual = userRegistration.register(ANY_MESSAGE_ID, user);
		assertNotNull(actual.getResponseContentUser());
		assertNull(actual.getResponseContentUser().getResponseMessage());

		verify(userGateway, times(1)).findUserByEmail(anyString(), anyString());
		verify(userGateway, times(1)).save(anyString(), any(User.class));
		verify(campaignGateway, never()).getCampaignsByTeam(anyString(), any(Integer.class));
	}
	
	@Test
	public void register_userExitsWithOutCampaigns_neverCallSaveMethodAndCallCampaignVerifier() {
		User user = new User();
		user.setEmail(ANY_USER_EMAIL);

		MessageContextUser anyMessageContextUser = new MessageContextUser(ANY_MESSAGE_ID, now(), user);
		when(userGateway.findUserByEmail(anyString(), anyString()))
			.thenReturn(Optional.of(anyMessageContextUser));
		
		List<Campaign> campaigns = Collections.singletonList(new Campaign(1));
		MessageContextCampaign anyMessageContextCampaign = new MessageContextCampaign(ANY_MESSAGE_ID, now(), campaigns);
		when(campaignGateway.getCampaignsByTeam(anyString(), any(Integer.class)))
			.thenReturn(Optional.of(anyMessageContextCampaign));
		
		MessageUserResponse actual = userRegistration.register(ANY_MESSAGE_ID, user);
		assertNotNull(actual.getResponseContentUser());
		assertEquals(USER_ALREADY_EXISTS, actual.getResponseContentUser().getResponseMessage());

		verify(userGateway, times(1)).findUserByEmail(anyString(), anyString());
		verify(userGateway, never()).save(anyString(), any(User.class));
		verify(campaignGateway, times(1)).getCampaignsByTeam(anyString(), any(Integer.class));
	}
	
	@Test
	public void register_userExitsWithCampaigns_neverCallSaveMethodAndNoverCallCampaignVerifier() {
		User user = new User();
		user.setEmail(ANY_USER_EMAIL);
		user.setCampagns(Collections.singletonList(new Campaign(1)));

		MessageContextUser anyMessageContextUser = new MessageContextUser(ANY_MESSAGE_ID, now(), user);
		when(userGateway.findUserByEmail(anyString(), anyString()))
			.thenReturn(Optional.of(anyMessageContextUser));

		List<Campaign> campaigns = Collections.singletonList(new Campaign(1));
		MessageContextCampaign anyMessageContextCampaign = new MessageContextCampaign(ANY_MESSAGE_ID, now(), campaigns);
		when(campaignGateway.getCampaignsByTeam(anyString(), any(Integer.class)))
			.thenReturn(Optional.of(anyMessageContextCampaign));

		MessageUserResponse actual = userRegistration.register(ANY_MESSAGE_ID, user);
		assertNotNull(actual.getResponseContentUser());
		assertEquals(USER_ALREADY_EXISTS, actual.getResponseContentUser().getResponseMessage());

		verify(userGateway, times(1)).findUserByEmail(anyString(), anyString());
		verify(userGateway, never()).save(anyString(), any(User.class));
		verify(campaignGateway, never()).getCampaignsByTeam(anyString(), any(Integer.class));
	}

	@Test
	public void addCampaigns_userExistsAndCampaignExist_callSaveMethod() {
		User user = new User();
		user.setEmail(ANY_USER_EMAIL);
		user.setCampagns(Collections.singletonList(new Campaign(1)));
		
		MessageContextUser anyMessageContextUser = new MessageContextUser(ANY_MESSAGE_ID, now(), user);
		when(userGateway.findUserByEmail(anyString(), anyString()))
			.thenReturn(Optional.of(anyMessageContextUser));
		
		MessageContextCampaign anyMessageContextCampaign = new MessageContextCampaign();
		when(campaignGateway.getCampaigns(anyString(), any(Integer.class)))
			.thenReturn(Optional.of(anyMessageContextCampaign));

		MessageUserResponse actual = userRegistration
				.addCampaigns(ANY_MESSAGE_ID, ANY_USER_EMAIL, ANY_CAMPAIGN_CODE);
		assertNotNull(actual.getResponseContentUser());
		assertNull(actual.getResponseContentUser().getResponseMessage());
		
		verify(campaignGateway, times(1)).getCampaigns(anyString(), any(Integer.class));
		verify(userGateway, times(1)).save(anyString(), any(User.class));
	}
	
	@Test
	public void addCampaigns_userExistsAndCampaignDoensExist_addMessageUserNotExistToResponse() {
		User user = new User();
		user.setEmail(ANY_USER_EMAIL);
//		user.setCampagns(Collections.singletonList(new Campaign(1)));
		
		MessageContextUser anyMessageContextUser = new MessageContextUser(ANY_MESSAGE_ID, now(), user);
		when(userGateway.findUserByEmail(anyString(), anyString()))
			.thenReturn(Optional.of(anyMessageContextUser));
		
		when(campaignGateway.getCampaigns(anyString(), any(Integer.class)))
			.thenReturn(Optional.ofNullable(null));

		MessageUserResponse actual = userRegistration.addCampaigns(ANY_MESSAGE_ID, ANY_USER_EMAIL, ANY_CAMPAIGN_CODE);
		
		assertNotNull(actual.getResponseContentUser());
		assertEquals(format(CAMPAIGN_NOT_EXISTS, 1), actual.getResponseContentUser().getResponseMessage());
		
		verify(campaignGateway, times(1)).getCampaigns(anyString(), any(Integer.class));
		verify(userGateway, never()).save(anyString(), any(User.class));
	}
	
	@Test
	public void addCampaigns_userDoenstExist_addMessageUserNotExistToResponse() {		
		when(userGateway.findUserByEmail(anyString(), anyString()))
			.thenReturn(Optional.ofNullable(MESSAGE_CONTEXT_NOT_PRESENT));

		MessageUserResponse actual = userRegistration.addCampaigns(ANY_MESSAGE_ID, ANY_USER_EMAIL, ANY_CAMPAIGN_CODE);
		
		assertNotNull(actual.getResponseContentUser());
		assertEquals(format(USER_NOT_EXISTS, ANY_USER_EMAIL), actual.getResponseContentUser().getResponseMessage());
		
		verify(campaignGateway, never()).getCampaigns(anyString(), any(Integer.class));
		verify(userGateway, never()).save(anyString(), any(User.class));
	}
}
