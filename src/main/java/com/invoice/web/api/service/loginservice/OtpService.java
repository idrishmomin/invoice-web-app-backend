package com.invoice.web.api.service.loginservice;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OtpService {

    private final Random random = new Random();

    @CachePut(value = "otpCache", key = "#email")
    public String generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(1000000)); // Generate a 6-digit OTP
        return otp; // Save OTP to cache with the phone number as the key
    }

    @Cacheable(value = "otpCache", key = "#email")
    public String getCachedOtp(String email) {
        return null; // Will return cached OTP if available, otherwise null
    }
}
