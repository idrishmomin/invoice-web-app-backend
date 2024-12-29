package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.ChangePassword;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.UserResponseDto;
import com.invoice.web.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Log4j2
@RestController
@RequestMapping("/webportal/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> userDetails(Principal principal) {
        log.info("Get UserDetails Request");
        return userService.userDetails(principal.getName());
    }
    @PutMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePassword changePassword, Principal principal) {
        log.info("Change password Request");
        return userService.changePassword(changePassword,false);
    }
}
