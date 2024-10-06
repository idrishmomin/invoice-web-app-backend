package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.ChangePassword;
import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.UpdateUserRequest;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.UserResponseDto;
import com.invoice.web.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody CreateUserRequest createUserRequest) {
        log.info("Create User Request : {}", createUserRequest);
        return userService.createUser(createUserRequest);
    }
    @PutMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable String email, @RequestBody UpdateUserRequest updateUserRequest) {
        log.info("Update User Request : {}", updateUserRequest);
        updateUserRequest.setCurrentEmail(email);
        return userService.updateUser(updateUserRequest);
    }

    @GetMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> userDetails(@PathVariable String email) {
        log.info("Get UserDetails Request");
        return userService.userDetails(email);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Page<UserResponseDto>>> userDetails(Pageable pageable) {
        log.info("Get All UserDetails Request");
        return userService.getAllUsers(pageable);
    }
    @DeleteMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String email) {
        log.info("Delete UserDetails Request");
        return userService.deleteUser(email);
    }

    @PutMapping(value = "/change-password/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> changePassword(@PathVariable("email") String email, @RequestBody ChangePassword changePassword) {
        log.info("Change password Request");
        changePassword.setEmail(email);
        return userService.changePassword(changePassword,true);
    }
}
