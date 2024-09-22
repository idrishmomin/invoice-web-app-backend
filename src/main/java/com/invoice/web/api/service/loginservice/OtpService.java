package com.invoice.web.api.service.loginservice;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private final Map<String, OtpDetails> otpStorage = new HashMap<>();

    // Expiry time for OTP (10 minutes)
    private final long OTP_EXPIRY_DURATION = TimeUnit.MINUTES.toMillis(10);

    public String generateOtp(String username) {
        // Generate a random 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store the OTP along with the current timestamp
        OtpDetails otpDetails = new OtpDetails(otp, System.currentTimeMillis());
        otpStorage.put(username, otpDetails);

        return otp;
    }

    public boolean validateOtp(String username, String otp) {
        OtpDetails otpDetails = otpStorage.get(username);

        // Check if OTP exists for the user
        if (otpDetails == null) {
            return false;
        }

        // Check if OTP is still valid (within expiry time)
        long currentTime = System.currentTimeMillis();
        if (currentTime - otpDetails.getTimestamp() > OTP_EXPIRY_DURATION) {
            otpStorage.remove(username);  // Remove expired OTP
            return false;
        }

        // Validate OTP
        return otpDetails.getOtp().equals(otp);
    }

}
