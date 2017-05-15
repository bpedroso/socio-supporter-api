package com.bpedroso.challenge.config.feign.exceptions;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.web.client.HttpStatusCodeException;

public class RemoteClientException extends HystrixBadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteClientException(HttpStatusCodeException cause) {
		super(cause.getMessage(), cause);
	}

	public boolean is4xx() {
		return getCause().getStatusCode().is4xxClientError();
	}

	public int getStatus() {
		return getCause().getStatusCode().value();
	}

	@Override
	public HttpStatusCodeException getCause() {
		return (HttpStatusCodeException) super.getCause();
	}
}
