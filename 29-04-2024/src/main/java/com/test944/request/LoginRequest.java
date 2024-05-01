package com.test944.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
