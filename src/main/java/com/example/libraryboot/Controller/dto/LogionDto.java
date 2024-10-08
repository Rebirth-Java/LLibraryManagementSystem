package com.example.libraryboot.Controller.dto;

import org.omg.PortableInterceptor.INACTIVE;

public class LogionDto {
    private Integer ID;
    private  String username;
    private  String phone;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    private  String email;
private  String Token;
    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        this.ID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
