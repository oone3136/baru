package com.test944.controller;

import com.test944.model.Users;
import com.test944.request.LoginRequest;
import com.test944.request.RefreshTokenRequest;
import com.test944.request.SignupRequest;
import com.test944.response.JwtResponse;
import com.test944.security.jwt.JwtUtils;
import com.test944.services.UserDetailsImpl;
import com.test944.services.UserDetailsServiceImpl;
import com.test944.services.UsersServis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        UsersServis service;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        private UserDetailsServiceImpl userDetailsServiceImpl;

        @Autowired
        JwtUtils jwtUtils;

        @PostMapping("/signin")
        public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request) {
                log.debug("start signin ");
                Authentication authentication = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                request.getPassword()));
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                String token = jwtUtils.generateJwtToken(authentication);
                String refreshToken = jwtUtils.generateRefresJwtToken(authentication);
                UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
                log.debug("token {}", token);
                log.debug("refresh token {}", refreshToken);
                log.debug("end login {}", authentication);
                return ResponseEntity.ok()
                                .body(new JwtResponse(token, refreshToken, principal.getUsername(),
                                                principal.getEmail(),
                                                principal.getRoles()));

        }

        @PostMapping("/signup")
        public Users signup(@RequestBody SignupRequest request) {
                log.debug("start signup {}", request);
                Users pengguna = new Users();
                pengguna.setId(request.getUsername());
                pengguna.setEmail(request.getEmail());
                pengguna.setPassword(passwordEncoder.encode(request.getPassword()));
                pengguna.setNamaLengkap(request.getNamaLengkap());
                pengguna.setNomorTelepon(request.getNomorTelepon());
                Users created = service.createUsers(pengguna);
                log.debug("end signup {}", created);
                return created;
        }

        @PostMapping("/refreshToken")
        public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
                String token = request.getRefreshToken();
                boolean valid = jwtUtils.validateJwtToken(token);
                if (!valid) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                String username = jwtUtils.getUserNameFromJwtToken(token);
                UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsImpl, null,
                                userDetailsImpl.getAuthorities());
                String newToken = jwtUtils.generateJwtToken(authentication);
                String refreshToken = jwtUtils.generateRefresJwtToken(authentication);
                return ResponseEntity.ok(new JwtResponse(newToken, refreshToken, username, userDetailsImpl.getEmail(),
                                userDetailsImpl.getRoles()));
        }
}
