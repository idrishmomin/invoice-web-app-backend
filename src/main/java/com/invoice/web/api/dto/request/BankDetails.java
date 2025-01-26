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

@Getter
@Setter
@NoArgsConstructor
@GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, BankDetails.class})
public class BankDetails {

    @NotBlank(message = "Bank Name should not be blank or null", groups = BlankCheck.class)
    private String bankName;

    @NotBlank(message = "Iban number should not be blank or null", groups = BlankCheck.class)
    private String ibanNumber;

    @NotBlank(message = "Bank Address should not be blank or null", groups = BlankCheck.class)
    private String bankAddress;
}