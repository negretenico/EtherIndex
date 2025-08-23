package com.negretenico.ether.index.converter;

import com.common.functionico.risky.Try;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (Objects.isNull(attribute) || attribute.isEmpty()) {
			return "[]";
		}
		return  Try.of(()->objectMapper.writeValueAsString(attribute))
				.getOrThrow();
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (Objects.isNull(dbData)|| dbData.trim().isEmpty()) {
			return new ArrayList<>();
		}
	return	Try.of(()->objectMapper.readValue(dbData,
				new TypeReference<List<String>>() {}))
				.getOrThrow();
	}
}