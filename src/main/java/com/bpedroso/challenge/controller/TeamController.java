package com.bpedroso.challenge.controller;

import static com.bpedroso.challenge.controller.constants.ControllerConstants.TEAM;
import static com.bpedroso.challenge.controller.constants.ControllerConstants.V1;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bpedroso.challenge.contracts.controller.MessageContext;
import com.bpedroso.challenge.contracts.controller.Team;
import com.bpedroso.challenge.exceptions.NoContentException;
import com.bpedroso.challenge.usecases.TeamRegistration;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = V1 + TEAM)
public class TeamController {

	private final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	private TeamRegistration useCaseTeamRegistration;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "messageId", value = "Message ID para rastreamento", required = false, dataType = "String", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = MessageContext.class),
			@ApiResponse(code = 204, message = "Success", response = MessageContext.class),
			@ApiResponse(code = 500, message = "Failure", response = MessageContext.class) })
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<MessageContext> insert(@RequestHeader(value = "messageId", required = false) String messageId,
			@RequestBody Team payLoad) {
		ResponseEntity<MessageContext> responseEntity;
		try {
			this.useCaseTeamRegistration.register(payLoad);

			responseEntity = new ResponseEntity<MessageContext>(new MessageContext(messageId, now()), OK);
		} catch (IOException e) {
			LOGGER.warn(e.getMessage());
			responseEntity = new ResponseEntity<MessageContext>(new MessageContext(messageId, now()), BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Fail to response " + e.getMessage());
			responseEntity = new ResponseEntity<MessageContext>(new MessageContext(messageId, now(), e.getMessage()),
					INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "messageId", value = "Message ID para rastreamento", required = false, dataType = "String", paramType = "header"),
			@ApiImplicitParam(name = "id", value = "Código do time", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "Nome do time", required = false, dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = MessageContext.class),
			@ApiResponse(code = 204, message = "Success", response = MessageContext.class),
			@ApiResponse(code = 500, message = "Failure", response = MessageContext.class) })
	@GetMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<MessageContext> list(@RequestHeader(value = "messageId", required = false) String messageId,
			@RequestParam Integer id, @RequestParam String name) {
		ResponseEntity<MessageContext> responseEntity;
		try {
			this.useCaseTeamRegistration.list(id, name);

			responseEntity = new ResponseEntity<MessageContext>(new MessageContext(messageId, now()), OK);
		} catch (NoContentException e) {
			LOGGER.warn(e.getMessage());
			responseEntity = new ResponseEntity<MessageContext>(new MessageContext(messageId, now()), NO_CONTENT);
		} catch (Exception e) {
			LOGGER.error("Fail to response " + e.getMessage());
			responseEntity = new ResponseEntity<MessageContext>(new MessageContext(messageId, now(), e.getMessage()),
					INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

}
