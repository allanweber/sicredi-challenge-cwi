package com.allanweber.sicredi.config;

import org.bson.types.ObjectId;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        Converter<Date, String> dateFormat = new AbstractConverter<Date, String>() {
            protected String convert(Date source) {
                String dateFormat = "yyyy-MM-dd HH:mm:ss";
                return source == null ? null : new SimpleDateFormat(dateFormat).format(source);
            }
        };

        Converter<Long, Long> longParser = new AbstractConverter<Long, Long>() {
            protected Long convert(Long source) {
                return Objects.isNull(source) ? 0L : source;
            }
        };

        Converter<ObjectId, String> bsonObjectIdParser = new AbstractConverter<ObjectId, String>() {
            protected String convert(ObjectId source) {
                return Objects.isNull(source) ? "" : source.toHexString();
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(dateFormat);
        mapper.addConverter(longParser);
        mapper.addConverter(bsonObjectIdParser);
        return  mapper;
    }
}
