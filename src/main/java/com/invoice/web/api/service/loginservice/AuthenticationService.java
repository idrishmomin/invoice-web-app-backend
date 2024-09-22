package com.invoice.web.api.service.loginservice;

import com.invoice.web.infrastructure.utils.EmailService;
import com.invoice.web.persistence.model.User;
import com.invoice.web.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    public String loginWithUsernamePassword(String username, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(username));
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if password is correct
            if (user.getPassword().equals(password)) {
                // Generate OTP
                String otp = otpService.generateOtp(username);


                emailService.sendSimpleMail(otp);
                return "OTP has been sent to your registered email.";
            } else {
                throw new RuntimeException("Invalid username or password");
            }
        }
        throw new RuntimeException("User not found");
    }

    public boolean verifyOtp(String username, String otp) {
        return otpService.validateOtp(username, otp);
    }
}

