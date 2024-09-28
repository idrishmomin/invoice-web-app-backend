package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.UserResponseDto;
import com.invoice.web.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/webportal/v1/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    private final UserService userService ;
    @PostMapping(value = "/create-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody CreateUserRequest createInvoiceRequest) {
        log.info("Create User Request : {}", createInvoiceRequest);
        return userService.createUser(createInvoiceRequest);
    }

    //TODO
    @PutMapping(value = "/update-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@RequestBody CreateUserRequest createInvoiceRequest) {
        log.info("Update User Request : {}", createInvoiceRequest);
        return userService.createUser(createInvoiceRequest);
    }
}
