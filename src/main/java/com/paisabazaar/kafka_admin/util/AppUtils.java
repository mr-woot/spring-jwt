package com.paisabazaar.kafka_admin.util;

import org.springframework.stereotype.Service;

public interface AppUtils {
    void copyNonNullProperties(Object source, Object target);
    String[] getNullPropertyNames (Object source);
}
