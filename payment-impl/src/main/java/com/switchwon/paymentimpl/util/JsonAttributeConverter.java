package com.switchwon.paymentimpl.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.switchwon.paymentimpl.config.BeanConfig;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.avro.generic.GenericRecord;

@Converter
public class JsonAttributeConverter implements AttributeConverter<Object, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        //Avro 직렬화를 위한 설정 추가
        module.addSerializer(GenericRecord.class, new BeanConfig.AvroJsonSerializer());
        objectMapper.registerModule(module);
    }

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert attribute to JSON string", e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON string to attribute", e);
        }
    }
}