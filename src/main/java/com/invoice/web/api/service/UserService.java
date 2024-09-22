package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.request.UserForPayload;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.infrastructure.utils.validation.RequestParameterValidator;
import com.invoice.web.persistence.model.User;
import com.invoice.web.persistence.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response<Object> login(LoginRequest request) {
        RequestParameterValidator.loginRequest(request);


        return null;
    }

    public Response<Object> createUser(CreateUserRequest request) {

        User user = userRepository.findByEmail(request.getEmail());
        if (null != user) {
            return Response.builder().response("User Already exists").build();
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setSurname(request.getSurname());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setCountry(request.getCountry());
        newUser.setDepartment(request.getDepartment());
        newUser.setRole(request.getRole());
        newUser.setPassword(request.getPassword());
        userRepository.save(newUser);

        return Response.builder().response("User Created Successfully").build();
    }

    public Response<Object> userDetails(String email) {

        User user = userRepository.findByEmail(email);

        if (null == user) {
            return Response.builder().response("User Details not found").build();
        }

        UserForPayload userForPayload = new UserForPayload();
        userForPayload.setName(user.getName());
        userForPayload.setSurname(user.getSurname());
        userForPayload.setEmail(user.getEmail());
        userForPayload.setPhone(user.getPhone());
        userForPayload.setCountry(user.getCountry());
        userForPayload.setDepartment(user.getDepartment());
        userForPayload.setRole(user.getRole());

        return Response.builder().response(userForPayload).build();
    }
}
