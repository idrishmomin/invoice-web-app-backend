package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.infrastructure.utils.validation.RequestParameterValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    public Response<Object> login(LoginRequest request) {
        RequestParameterValidator.loginRequest(request);
        return null;
    }

    public Response<Object> createUser(CreateUserRequest request)
    {
        return null;
    }

    public Response<Object> userDetails() {
        return null;
    }
}
