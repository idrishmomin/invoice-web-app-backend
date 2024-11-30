package com.invoice.web.api.dto.request;

import com.invoice.web.infrastructure.utils.validation.BlankCheck;
import com.invoice.web.infrastructure.utils.validation.LengthCheck;
import com.invoice.web.infrastructure.utils.validation.NoNullStringCheck;
import com.invoice.web.infrastructure.utils.validation.PatternCheck;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, LoginRequest.class})
public class LoginRequest {

    @NotBlank(message = "Email should not be blank or null", groups = BlankCheck.class)
    private String email;

    @NotBlank(message = "Password should not be blank or null", groups = BlankCheck.class)
    private String password;

    //@NotBlank(message = "OTP should not be blank or null", groups = BlankCheck.class)
    private String otp;

}
