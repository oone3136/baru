package com.test944.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class SignupRequest implements Serializable {

    private String username;
    private String password;
    private String email;
    private String namaLengkap;
    private String nomorTelepon;
}
