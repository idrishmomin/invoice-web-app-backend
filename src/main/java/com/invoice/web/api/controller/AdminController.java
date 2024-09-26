package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.service.UserService;
import com.invoice.web.persistence.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/webportal/v1/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    private final UserService userService ;
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> createUser(@RequestBody CreateUserRequest createInvoiceRequest) {
        log.info("Create User Request : {}", createInvoiceRequest);
        return userService.createUser(createInvoiceRequest);
    }
}
