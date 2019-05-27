package com.paisabazaar.kafka_admin.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "*Please provide either username or email")
    private String usernameOrEmail;

    @NotBlank(message = "*Please provide your password")
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
