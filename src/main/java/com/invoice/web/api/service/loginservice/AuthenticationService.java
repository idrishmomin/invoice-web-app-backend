package com.invoice.web.api.service.loginservice;

import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.service.UserDetailsLoginService;
import com.invoice.web.infrastructure.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsLoginService userDetailsService;
    private final JwtUtils jwtUtils; // Token generation utility

    public UserDetails validateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return userDetailsService.loadUserByUsername(loginRequest.getEmail());
    }
    public String getJwtToken(UserDetails userDetails){
        if ( userDetails != null)
            //userDetails.getAuthorities().
            return jwtUtils.generateToken(userDetails);
        return null;
    }
}

