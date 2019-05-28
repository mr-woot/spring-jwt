package com.paisabazaar.kafka_admin.model;

public enum CurrentStatus {
    PENDING,
    ACTIVE,
    DISABLED
    ;

    private String status;
    CurrentStatus() {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
