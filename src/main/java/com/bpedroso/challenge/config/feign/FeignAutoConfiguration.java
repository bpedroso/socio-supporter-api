package com.bpedroso.challenge.config.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bpedroso.challenge.config.feign.decoder.OptionalAwareDecoder;
import com.bpedroso.challenge.config.feign.decoder.SpringWebClientErrorDecoder;
import com.bpedroso.challenge.config.feign.interceptor.MDCFeignInterceptor;

import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;

@Configuration
@EnableFeignClients
public class FeignAutoConfiguration {
	
	public static final int FIVE_SECONDS = 5000;

	@Bean
	public SpringWebClientErrorDecoder errorDecoder() {
		return new SpringWebClientErrorDecoder();
	}

	@Bean
	@ConditionalOnClass(name = "com.bpedroso.challenge.config.logging.LoggingAutoConfiguration")
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

	@Bean
    public Request.Options options() {
        return new Request.Options(FIVE_SECONDS, FIVE_SECONDS);
    }
}