package com.bpedroso.challenge.config.feign.decoder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.bpedroso.challenge.config.feign.exceptions.RemoteClientException;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;

import feign.Response;
import feign.codec.ErrorDecoder;

public class SpringWebClientErrorDecoder implements ErrorDecoder {

	private Logger log = LoggerFactory.getLogger(SpringWebClientErrorDecoder.class);
	
	private ErrorDecoder delegate = new Default();

	@Override
	public Exception decode(final String methodKey, final Response response) {
		HttpStatus statusCode = HttpStatus.valueOf(response.status());

		if (statusCode.is4xxClientError()) {
			byte[] responseBody = responseBody(response);
			HttpClientErrorException clientErrorException = new HttpClientErrorException(statusCode, response.reason(),
					responseHeaders(response), responseBody, null);
			logResponse(methodKey, statusCode, responseBody);
			return new RemoteClientException(clientErrorException);
		}

		if (statusCode.is5xxServerError()) {
			byte[] responseBody = responseBody(response);
			logResponse(methodKey, statusCode, responseBody);
			return new HttpServerErrorException(statusCode, response.reason(), responseHeaders(response), responseBody,
					null);
		}

		return delegate.decode(methodKey, response);
	}

	private void logResponse(String methodKey, HttpStatus statusCode, byte[] responseBody) {
		log.error("FeignDecoder=ERROR - Method={} response error statusCode={}, skipping fallback with response={}",
				methodKey, statusCode, new String(responseBody));
	}

	private HttpHeaders responseHeaders(final Response response) {
		HttpHeaders headers = new HttpHeaders();
		response.headers().entrySet().stream()
				.forEach(entry -> headers.put(entry.getKey(), Lists.newArrayList(entry.getValue())));
		return headers;
	}

	private byte[] responseBody(final Response response) {
		if (response.body() == null) {
			return new byte[0];
		}
		try {
			return ByteStreams.toByteArray(response.body().asInputStream());
		} catch (IOException e) {
			throw new HttpMessageNotReadableException("Feign decoder - Failed to process response body.", e);
		}
	}
}