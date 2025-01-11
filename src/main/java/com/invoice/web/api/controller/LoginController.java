package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.LoginResponse;
import com.invoice.web.api.service.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/webportal/v1")
@AllArgsConstructor
public class LoginController {

    private final UserService userService;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) throws MessagingException {
        log.info("Login Request : {}", loginRequest);
        return userService.login(loginRequest);
    }

}
