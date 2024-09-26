package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.request.UserForPayload;
import com.invoice.web.api.dto.response.LoginResponse;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.enums.Roles;
import com.invoice.web.api.service.loginservice.AuthenticationService;
import com.invoice.web.api.service.loginservice.OtpService;
import com.invoice.web.infrastructure.Constants;
import com.invoice.web.infrastructure.utils.EmailService;
import com.invoice.web.infrastructure.utils.validation.RequestParameterValidator;
import com.invoice.web.persistence.model.Role;
import com.invoice.web.persistence.model.User;
import com.invoice.web.persistence.repositories.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder ;
    private final AuthenticationService authenticationService;
    private final OtpService otpService ;
    private final EmailService emailService ;


    /*public Response<Object> createUser(CreateUserRequest createUserRequest) {
        String rawPassword = createUserRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return null;
    }*/

    public ResponseEntity<LoginResponse> login(LoginRequest request) throws MessagingException {
        RequestParameterValidator.loginRequest(request);
        UserDetails userDetails = null ;
        try {
            userDetails = authenticationService.validateUser(request);
        } catch (Exception e){
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(Constants.INVALID_USER));
        }
        if (Objects.nonNull(userDetails) && Objects.nonNull(request.getOtp()) && !request.getOtp().isEmpty()){
            if ( request.getOtp().equals(otpService.getCachedOtp(request.getEmail()))) {
                String token = authenticationService.getJwtToken(userDetails) ;
                return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(Constants.SUCCESS,token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(Constants.INVALID_OTP));
            }
        } else if(Objects.nonNull(userDetails) && (Objects.isNull(request.getOtp()) || request.getOtp().isEmpty())){
            String otp = otpService.generateOtp(userDetails.getUsername());
            User user = userRepository.findByEmail(userDetails.getUsername());
            emailService.sendOtpEmail(user.getName(),user.getSurname(),otp,userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(Constants.OTP_GENERATED));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(Constants.INVALID_USER));
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

        newUser.setRoles(request.getRoles().stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setRoleType(Roles.valueOf(roleName.toUpperCase()));
                    return role;
                }).collect(Collectors.toSet()));

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
        userForPayload.setCountry(user.getPhone());
        userForPayload.setDepartment(user.getDepartment());

        userForPayload.setRoles(user.getRoles().stream()
                .map(role -> role.getRoleType().name())
                .collect(Collectors.toList()));

        return Response.builder().response(userForPayload).build();
    }
}
