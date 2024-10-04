package com.invoice.web.api.controller;

import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.UserResponseDto;
import com.invoice.web.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Log4j2
@RestController
@RequestMapping("/webportal/v1/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> userDetails(Principal principal) {
        log.info("Get UserDetails Request");
        return userService.userDetails(principal.getName());
    }
}
