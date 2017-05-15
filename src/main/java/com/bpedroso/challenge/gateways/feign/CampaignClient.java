package com.bpedroso.challenge.gateways.feign;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpedroso.challenge.contracts.campaignapi.Campaign;
import com.bpedroso.challenge.contracts.campaignapi.MessageContextCampaign;

@FeignClient(name = "${feign.campaign.name}", url = "${feign.campaign.url:}", fallback = CampaignFallback.class)
public interface CampaignClient {

//GET http://localhost:8081/v1/campaign
//GET http://localhost:8081/v1/campaign?code=1
  @RequestMapping(method = GET, value = "${api.campaignapi.path}", produces = APPLICATION_JSON_UTF8_VALUE)
  MessageContextCampaign getCampaigns(@RequestHeader String messageId, @RequestParam(value="code", required=false) int code);

//DELETE http://localhost:8081/v1/campaign?code=1  
  @RequestMapping(method = DELETE, value = "${api.campaignapi.path}", produces = APPLICATION_JSON_UTF8_VALUE)
  MessageContextCampaign deleteCampaign(@RequestHeader String messageId, @RequestParam(value="code", required=false) int code);

//  POST http://localhost:8081/v1/campaign
//  payLoad: {
//      "beginDate": "2017-05-14",
//      "code": 1,
//      "endDate": "2017-05-14",
//      "idTeam": 1,
//      "name": "Nome da campanha"
//  }
  @RequestMapping(method = POST, value = "${api.campaignapi.path}", produces = APPLICATION_JSON_UTF8_VALUE)
  MessageContextCampaign createCampaign(@RequestHeader String messageId, 
		  @RequestBody Campaign campaign);

}
