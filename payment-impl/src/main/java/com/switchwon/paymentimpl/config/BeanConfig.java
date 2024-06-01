package com.switchwon.paymentimpl.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BeanConfig {


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        //Avro 직렬화를 위한 설정 추가
        module.addSerializer(GenericRecord.class, new AvroJsonSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    public static class AvroJsonSerializer extends JsonSerializer<GenericRecord> {

        @Override
        public void serialize(GenericRecord value, com.fasterxml.jackson.core.JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            Schema schema = value.getSchema();
            for (Schema.Field field : schema.getFields()) {
                gen.writeObjectField(field.name(), value.get(field.name()));
            }
            gen.writeEndObject();
        }
    }
}
