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
@Setter
@Getter
@NoArgsConstructor
@GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, VendorCreateRequest.class})
public class VendorCreateRequest {

    private String id;

    @NotBlank(message = "Vendor ID should not be blank or null", groups = BlankCheck.class)
    private String vendorId;

    @NotBlank(message = "VendorName should not be blank or null", groups = BlankCheck.class)
    private String vendorName;

    @NotBlank(message = "Address should not be blank or null", groups = BlankCheck.class)
    private String address;

    @NotBlank(message = "Phone Number should not be blank or null", groups = BlankCheck.class)
    private String phoneNumber;

    private BankDetails bankDetails;

}
