package com.invoice.web.api.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class UserForPayload {

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String country;

    private String department;

    private String role;
}
