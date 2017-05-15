package com.bpedroso.challenge.config.feign.interceptor;

import java.util.Map;

import com.bpedroso.challenge.config.logging.LogField;
import com.bpedroso.challenge.config.logging.mdc.MDCRepository;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class MDCFeignInterceptor implements RequestInterceptor {

	  private static final String HEADER_USER_AGENT = "User-Agent";

	  @Override
	  public void apply(final RequestTemplate requestTemplate) {
	    Map<LogField, String> data = MDCRepository.findAllLogData();
	    data.entrySet().forEach(entry -> {
	      final String key = LogField.USER_AGENT == entry.getKey() ? HEADER_USER_AGENT : entry.getKey().key();
	      requestTemplate.header(key, entry.getValue());
	    });
	  }
	}