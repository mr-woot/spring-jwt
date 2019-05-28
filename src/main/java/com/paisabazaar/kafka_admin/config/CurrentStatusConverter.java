package com.paisabazaar.kafka_admin.config;

import com.paisabazaar.kafka_admin.model.CurrentStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CurrentStatusConverter implements AttributeConverter<CurrentStatus, String> {

    @Override
    public String convertToDatabaseColumn(CurrentStatus status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
    }

    @Override
    public CurrentStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(CurrentStatus.values())
                .filter(c -> c.equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}