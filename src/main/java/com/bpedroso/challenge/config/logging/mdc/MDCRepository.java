package com.bpedroso.challenge.config.logging.mdc;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.MDC;

import com.bpedroso.challenge.config.logging.LogField;

public class MDCRepository {

	private MDCRepository() {
	}

	public static void addAllLogData(final Map<LogField, String> logData) {
		logData.entrySet().forEach(entry -> MDC.put(entry.getKey().key(), entry.getValue()));
	}

	public static void removeAllLogData() {
		for (LogField logField : LogField.values()) {
			MDC.remove(logField.key());
		}
	}

	public static Map<LogField, String> findAllLogData() {
		final Map<LogField, String> logFieldData = new EnumMap<>(LogField.class);
		for (LogField logField : LogField.values()) {
			final String value = MDC.get(logField.key());
			if (!isNullOrEmpty(value)) {
				logFieldData.put(logField, value);
			}
		}
		return logFieldData;
	}
}
