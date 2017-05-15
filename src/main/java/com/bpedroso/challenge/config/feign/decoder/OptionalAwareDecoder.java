package com.bpedroso.challenge.config.feign.decoder;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import feign.Response;
import feign.Util;
import feign.codec.Decoder;

public class OptionalAwareDecoder implements Decoder {

	  private final Decoder decoder;

	  public OptionalAwareDecoder(final Decoder decoder) {
	    this.decoder = decoder;
	  }

	  static boolean isOptional(Type type) {
	    if (type instanceof ParameterizedType) {
	      ParameterizedType parameterizedType = (ParameterizedType) type;
	      return parameterizedType.getRawType().equals(Optional.class);
	    }
	    return false;
	  }

	  @Override
	  public Object decode(Response response, Type type) throws IOException {
	    if (isOptional(type)) {
	      Type upperType = Util.resolveLastTypeParameter(type, Optional.class);
	      return Optional.of(decoder.decode(response, upperType));
	    }
	    return decoder.decode(response, type);
	  }

	}