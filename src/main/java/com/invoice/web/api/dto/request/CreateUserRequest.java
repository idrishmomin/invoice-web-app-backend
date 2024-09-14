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
@GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, CreateUserRequest.class})
public class CreateUserRequest {


    @NotBlank(message = "Name should not be blank or null", groups = BlankCheck.class)
    private String name;

    @NotBlank(message = "Surname should not be blank or null", groups = BlankCheck.class)
    private String surname;

    @NotBlank(message = "Email should not be blank or null", groups = BlankCheck.class)
    private String email;

    @NotBlank(message = "Phone No should not be blank or null", groups = BlankCheck.class)
    private String phone;

    @NotBlank(message = "Country should not be blank or null", groups = BlankCheck.class)
    private String country;

    @NotBlank(message = "Department should not be blank or null", groups = BlankCheck.class)
    private String department;

    @NotBlank(message = "Role should not be blank or null", groups = BlankCheck.class)
    private String role;

}
