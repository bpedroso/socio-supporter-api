package com.bpedroso.challenge.config;

public enum ErrorCode {

	UNDEFINED_ERROR("TEAMPARTNERAPI_ERROR1", "Erro desconhecido"),
	DATE_FORMAT_ERROR("TEAMPARTNERAPI_ERROR2", "Formato de data invalido YYYY-MM-DDThh:mm:ssZ"),
	EXT_LIST_ERROR("TEAMPARTNERAPI_ERROR3", "Fail to list products"),
	NO_CONTENT("TEAMPARTNERAPI_ERROR4", "No content from source");

	private String code;
	private String description;

	ErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
}
