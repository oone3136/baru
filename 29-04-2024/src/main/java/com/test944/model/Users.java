package com.test944.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class Users {

     @Id
     private String id;

     @Column(name = "nama_Lengkap")
     private String namaLengkap;

     @Column(name = "nomor_telepon")
     private String nomorTelepon;

     @Column(name = "email")
     private String email;

     @Column(name ="password", nullable = false)
     private String password;

     @JsonIgnore
     private String roles;
     @JsonIgnore
     private Boolean isAktif;

     public Users(String username) {
          this.id = username;
     }
     public Users(String id, String namaLengkap, String email, String devisi, String nomorTelepon, String password)
     {
          this.id = id;
          this.namaLengkap = namaLengkap;
          this.email = email;
          this.nomorTelepon = nomorTelepon;
          this.password = password;
     }

     public Users(String id, String namaLengkap, String email, String nomorTelepon, String password, String roles, Boolean isAktif) {
          this.id = id;
          this.namaLengkap = namaLengkap;
          this.email = email;
          this.nomorTelepon = nomorTelepon;
          this.password = password;
          this.roles = roles;
          this.isAktif = isAktif;
     }
}
