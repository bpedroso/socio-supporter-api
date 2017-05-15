package com.bpedroso.challenge.usecases;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bpedroso.challenge.contracts.controller.Team;
import com.bpedroso.challenge.exceptions.NoContentException;
import com.bpedroso.challenge.repository.TeamRepository;

@Component
public class TeamRegistration {
	
	private final Logger log = LoggerFactory.getLogger(TeamRegistration.class);

	private TeamRepository teamRepository;
	
	@Autowired
	public TeamRegistration(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public void register(Team payLoad) throws IOException {
		this.log.info("Creating/Updating team: {}", payLoad);
		this.teamRepository.save(payLoad);
	}
	
	public Team list(Integer id, String name) throws NoContentException {
		this.log.info("Find team: id={} - name={}", id, name);
		Team team = null;
		if(id != null)
			team = this.teamRepository.findById(id);
		else if (isNotEmpty(name))
			team = this.teamRepository.findByName(name);
		else
			throw new NoContentException("Team not found for id=" + id + " and/or name=" + name);

		return team;
	}
}
