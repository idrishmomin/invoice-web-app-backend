package com.invoice.web.api.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankDetails {
    private String bankName;
    private String ibanNumber;
    private String bankAddress;
}