package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.persistence.model.User;
import com.invoice.web.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;

    public Response<Object> login(LoginRequest loginRequest) {
        return null;
    }

    public Response<Object> createUser(CreateUserRequest createUserRequest) {
        String rawPassword = createUserRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return null;
    }
}
