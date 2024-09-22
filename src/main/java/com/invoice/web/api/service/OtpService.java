package com.invoice.web.api.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtpToEmail(String email, String otp) {
        // TODO
        System.out.println("Sending OTP to email: " + email);
    }
}

