package com.paisabazaar.kafka_admin.payload;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class UpdateUserRequest {
    @Length(min = 3, max = 40, message = "*Your name must have atleast 3 and atmost 40 characters")
    private String name;

    @Length(min = 3, max = 15, message = "*Your username must have atleast 3 characters")
    private String username;

    @Length(min = 7, max = 40, message = "*Your email must have at least 7 characters")
    @Email
    private String email;

    @Length(min = 5, max = 30, message = "*Your password must have at least 5 characters")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
