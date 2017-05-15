package com.bpedroso.challenge.config.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import com.bpedroso.challenge.config.feign.decoder.OptionalAwareDecoder;
import com.bpedroso.challenge.config.feign.decoder.SpringWebClientErrorDecoder;
import com.bpedroso.challenge.config.feign.interceptor.MDCFeignInterceptor;

import feign.Retryer;
import feign.jackson.JacksonDecoder;

public class FeignAutoConfiguration {

	  @Bean
	  public SpringWebClientErrorDecoder errorDecoder() {
	    return new SpringWebClientErrorDecoder();
	  }

	  @Bean
	  @ConditionalOnClass(name = "com.carrefour.spring.boot.autoconfigure.logging.LoggingAutoConfiguration")
	  public MDCFeignInterceptor mdcFeignInterceptor() {
	    return new MDCFeignInterceptor();
	  }

	  @Bean
	  public OptionalAwareDecoder decoder() {
	    return new OptionalAwareDecoder(new JacksonDecoder());
	  }

	  @Bean
	  public Retryer retryer() {
	    return Retryer.NEVER_RETRY;
	  }
	}