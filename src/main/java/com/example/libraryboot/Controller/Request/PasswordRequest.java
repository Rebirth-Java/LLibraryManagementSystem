package com.example.libraryboot.Controller.Request;

import lombok.Data;

@Data
public class PasswordRequest {
    private  String username;
    private  String password;
    private  String newPass;

}
