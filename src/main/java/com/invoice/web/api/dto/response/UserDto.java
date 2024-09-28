package com.invoice.web.api.dto.response;

import com.invoice.web.api.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String country;
    private String phoneNumber;
    private String department;
    private List<Roles> roles;
}
