package com.bpedroso.challenge.controller;

import static com.bpedroso.challenge.controller.constants.ControllerConstants.USER;
import static com.bpedroso.challenge.controller.constants.ControllerConstants.V1;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bpedroso.challenge.contracts.controller.MessageUserResponse;
import com.bpedroso.challenge.contracts.controller.ResponseContentUser;
import com.bpedroso.challenge.contracts.controller.User;
import com.bpedroso.challenge.contracts.controller.UserCampaign;
import com.bpedroso.challenge.usecases.UserRegistration;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = V1 + USER)
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRegistration useCaseUserRegistration;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "messageId", value = "Message ID para rastreamento", required = false, dataType = "String", paramType = "header"),
			@ApiImplicitParam(name = "payLoad", value = "User contract", required = false, dataType = "User", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = MessageUserResponse.class),
			@ApiResponse(code = 204, message = "Success", response = MessageUserResponse.class),
			@ApiResponse(code = 500, message = "Failure", response = MessageUserResponse.class) })
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<MessageUserResponse> insert(@RequestHeader(value = "messageId", required = false) String messageId,
			@RequestBody User payLoad) {
		ResponseEntity<MessageUserResponse> responseEntity;
		try {
			responseEntity = new ResponseEntity<MessageUserResponse>(this.useCaseUserRegistration.register(messageId, payLoad), OK);
		} catch (Exception e) {
			LOGGER.error("Fail to response " + e.getMessage());
			responseEntity = new ResponseEntity<MessageUserResponse>(new MessageUserResponse(messageId, now(), new ResponseContentUser(e.getMessage())),
					INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "messageId", value = "Message ID para rastreamento", required = false, dataType = "String", paramType = "header"),
		@ApiImplicitParam(name = "payLoad", value = "User contract", required = false, dataType = "UserCampaign", paramType = "body") })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = MessageUserResponse.class),
		@ApiResponse(code = 204, message = "Success", response = MessageUserResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = MessageUserResponse.class) })
@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public ResponseEntity<MessageUserResponse> addCampaign(@RequestHeader(value = "messageId", required = false) String messageId,
		@RequestBody UserCampaign payLoad) {
	ResponseEntity<MessageUserResponse> responseEntity;
	try {
			responseEntity = new ResponseEntity<MessageUserResponse>(this.useCaseUserRegistration
					.addCampaigns(messageId, payLoad.getIdUser(), payLoad.getCampaignCode()), OK);
	} catch (Exception e) {
		LOGGER.error("Fail to response " + e.getMessage());
		responseEntity = new ResponseEntity<MessageUserResponse>(new MessageUserResponse(messageId, now(), new ResponseContentUser(e.getMessage())),
				INTERNAL_SERVER_ERROR);
	}
	return responseEntity;
}
}
