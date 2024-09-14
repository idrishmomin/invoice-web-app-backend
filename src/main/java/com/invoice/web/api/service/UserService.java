package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    public Response<Object> login(LoginRequest loginRequest) {
        return null;
    }

    public Response<Object> createUser(CreateInvoiceRequest createInvoiceRequest) {
        return null;
    }

    public Response<Object> userDetails() {
        return null;
    }
}
