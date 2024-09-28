package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.request.UserForPayload;
import com.invoice.web.api.dto.response.*;
import com.invoice.web.api.enums.Roles;
import com.invoice.web.api.service.loginservice.AuthenticationService;
import com.invoice.web.api.service.loginservice.OtpService;
import com.invoice.web.infrastructure.Constants;
import com.invoice.web.infrastructure.utils.EmailService;
import com.invoice.web.infrastructure.utils.validation.RequestParameterValidator;
import com.invoice.web.persistence.model.User;
import com.invoice.web.persistence.repositories.RoleRepository;
import com.invoice.web.persistence.repositories.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;
    private final OtpService otpService ;
    private final EmailService emailService ;

    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request) throws MessagingException {
        RequestParameterValidator.loginRequest(request);
        UserDetails userDetails = null ;
        try {
            userDetails = authenticationService.validateUser(request);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(Constants.INVALID_USER));
        }
        if (Objects.nonNull(userDetails) && Objects.nonNull(request.getOtp()) && !request.getOtp().isEmpty()){
            if ( request.getOtp().equals(otpService.getCachedOtp(request.getEmail()))) {
                String token = authenticationService.getJwtToken(userDetails) ;
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(Constants.SUCCESS,new LoginResponse(token)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(Constants.INVALID_OTP));
            }
        } else if(Objects.nonNull(userDetails) && (Objects.isNull(request.getOtp()) || request.getOtp().isEmpty())){
            String otp = otpService.generateOtp(userDetails.getUsername());
            User user = userRepository.findByEmail(userDetails.getUsername());
            emailService.sendOtpEmail(user.getName(),user.getSurname(),otp,userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(Constants.OTP_GENERATED));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(Constants.INVALID_USER));
    }

    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(CreateUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (null != user) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body( new ApiResponse<>(Constants.DUPLICATE_USER));
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setSurname(request.getSurname());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setCountry(request.getCountry());
        newUser.setDepartment(request.getDepartment());
        List<Roles> roles = request.getRoles().stream()
                        .map(roleString -> Roles.valueOf(roleString.toUpperCase()))
                                .collect(Collectors.toList());
        newUser.setRoles(roleRepository.findByRoleTypeIn(roles));

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        User createdUser = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body( new ApiResponse<>(Constants.SUCCESS,
                        new UserResponseDto(convertUserEntityToDto(createdUser))));
    }

    public ResponseEntity<ApiResponse<UserResponseDto>> userDetails(String email) {
        User user = userRepository.findByEmail(email);
        if (null == user) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body( new ApiResponse<>(Constants.USER_NOT_FOUND));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body( new ApiResponse<>(Constants.SUCCESS,
                        new UserResponseDto(convertUserEntityToDto(user))));
    }
    private UserDto convertUserEntityToDto(User user){
        UserDto userDetails = new UserDto();
        userDetails.setPhoneNumber(user.getPhone());
        userDetails.setDepartment(user.getDepartment());
        userDetails.setName(user.getName());
        userDetails.setSurname(user.getSurname());
        userDetails.setEmail(user.getEmail());
        userDetails.setCountry(user.getCountry());
        userDetails.setRoles(
                user.getRoles()
                        .stream().map(userRole->
                                Roles.valueOf(userRole.getRoleType()
                                        .toString().toUpperCase()))
                        .collect(Collectors.toList())
        );
        return userDetails;
    }
}
