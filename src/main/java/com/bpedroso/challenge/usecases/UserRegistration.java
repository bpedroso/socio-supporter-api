package com.bpedroso.challenge.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bpedroso.challenge.gateways.CampaignGateway;
import com.bpedroso.challenge.repository.UserRepository;

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

	private UserRepository userRepository;
	
	@Autowired
	public UserRegistration(CampaignGateway productGateway, UserRepository userRepository) {
		this.campaignGateway = productGateway;
		this.userRepository = userRepository;
	}
	
//	public MessageContext find(int start, int rows, String fields, final String fieldFilter) {
//	final Optional<SearchResponseContext> products = this.campaignGateway.listProducts(start, rows, this.apiSaasConfiguration.getQuerystring().getQ(),
//				this.apiSaasConfiguration.getQuerystring().getWt(), this.listProductsHelper.replaceFields(fields),
//				this.listProductsHelper.adaptFilterField(fieldFilter));
//	
//	if(products.isPresent()) {
//		SearchResponseContent searchResponse = products.get().getSearchResponseContent();
//		
//		final List<String> codes = searchResponse.getContent().stream().map(Content::getCode)
//				.collect(Collectors.toList());
//		
//		
//	}
//
//	return products.map(saasResponse -> new MessageContextCampaign(null, Instant.now().toString(),
//					this.mapper.map(saasResponse.getSearchResponseContent().getContent()),
//					this.listProductsHelper.buildPaginationResponse(saasResponse, rows)))
//			.orElseThrow(() -> new NoContentException(NO_CONTENT.getDescription()));
//}

}
