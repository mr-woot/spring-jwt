package com.paisabazaar.kafka_admin.model;

public enum CurrentStatus {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    DISABLED("DISABLED")
    ;

    private String status;
    CurrentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
