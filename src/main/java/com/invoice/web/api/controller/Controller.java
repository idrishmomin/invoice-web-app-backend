package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.response.LoginResponse;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.service.GenerateInvoiceService;
import com.invoice.web.api.service.InvoiceService;
import com.invoice.web.api.service.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/webportal/v1")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class Controller {

    private final UserService userService;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws MessagingException {
        log.info("Login Request : {}", loginRequest);
        return userService.login(loginRequest);
        /*if (loginRequest.getOtp() == null || loginRequest.getOtp().isEmpty()) {
            // First step: login with username and password
            String message = "";//authenticationService.loginWithUsernamePassword(loginRequest.getEmail(), loginRequest.getPassword());
            return Response.builder().response(message).build();
        } else {
            // Second step: verify OTP
            boolean isOtpValid =true ;// authenticationService.verifyOtp(loginRequest.getEmail(), loginRequest.getOtp());
            if (isOtpValid) {
                // Generate JWT token upon successful OTP verification
                String jwtToken = "";//jwtUtil.generateToken(loginRequest.getEmail());
                return Response.builder().response(jwtToken).build();
            } else {
                return Response.builder().response("Invalid OTP").build();
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
        }*/
    }

}
