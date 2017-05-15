package com.bpedroso.challenge.configuration;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.bpedroso.challenge.config.ErrorCode;

public class ErrorCodeTest {

	private static final String UNEXPECTED_VALUE = "";
	ErrorCode errorCodeUndefined = ErrorCode.UNDEFINED_ERROR;
	ErrorCode errorCodeDate = ErrorCode.DATE_FORMAT_ERROR;

	@Test
	public void getCode_validateCodeExists_returnsAnyCode() {
		assertNotEquals(UNEXPECTED_VALUE, errorCodeUndefined.getCode());
		assertNotEquals(UNEXPECTED_VALUE, errorCodeDate.getCode());
	}

	@Test
	public void getDescription_validateDescriptionExists_returnsAnyDescription() {
		assertNotEquals(UNEXPECTED_VALUE, errorCodeUndefined.getDescription());
		assertNotEquals(UNEXPECTED_VALUE, errorCodeDate.getDescription());
	}

}
