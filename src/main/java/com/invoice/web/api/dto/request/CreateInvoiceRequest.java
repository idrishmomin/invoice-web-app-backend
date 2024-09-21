package com.invoice.web.api.dto.request;

import com.invoice.web.infrastructure.utils.validation.*;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, CreateInvoiceRequest.class})
public class CreateInvoiceRequest {

    private String invoiceNumber;

    @NotBlank(message = "VendorInvoiceDate Can not be blank", groups = BlankCheck.class)
    private String vendorInvoiceDate;

    @Valid
    private Total total;

    @Valid
    private AccountDue accountDue;

    @Valid
    private Submitter submitter;

    private Submitter updatedBy;

    @Valid
    private List<Item> items;


    // SubTotal class
    @Setter
    @Getter
    @NoArgsConstructor
    @GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, Total.class})
    public static class Total {

        @NotBlank(message = "SubTotal Can not be blank", groups = BlankCheck.class)
        private String subTotal;

        @NotBlank(message = "Adjustments Can not be blank", groups = BlankCheck.class)
        private String adjustments;

        @NotBlank(message = "Grand Total Can not be blank", groups = BlankCheck.class)
        private String grandTotal;
    }


    // AccountDue class
    @Setter
    @Getter
    @NoArgsConstructor
    @GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, AccountDue.class})
    public static class AccountDue {

        @NotBlank(message = "Account Type Can not be blank", groups = BlankCheck.class)
        private String accountType;

        @NotBlank(message = "Account Due Can not be blank", groups = BlankCheck.class)
        private String totalDue;

        @NotBlank(message = "Payment Type Can not be blank", groups = BlankCheck.class)
        private String paymentType;
    }

    // Submitter class
    @Setter
    @Getter
    @NoArgsConstructor
    @GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, Submitter.class})
    public static class Submitter {

        @NotBlank(message = "Submitter Name Can not be blank", groups = BlankCheck.class)
        private String submitterName;

        @NotBlank(message = "Department Can not be blank", groups = BlankCheck.class)
        private String department;
    }

    // Item class
    @Setter
    @Getter
    @NoArgsConstructor
    @GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, Item.class})
    public static class Item {
        private String refId;

        @NotBlank(message = "Vendor Invoice Ref Can not be blank", groups = BlankCheck.class)
        private String vendorInvoiceRef;
        private String rateOfSAR;

        @NotBlank(message = "Vendor Id Can not be blank", groups = BlankCheck.class)
        private String vendorId;

        @NotBlank(message = "Cost Code Can not be blank", groups = BlankCheck.class)
        private String costCode;

        @NotBlank(message = "Expense Code Can not be blank", groups = BlankCheck.class)
        private String expenseCode;

        @NotBlank(message = "Quantity Code Can not be blank", groups = BlankCheck.class)
        private String quantity;

        @NotBlank(message = "Amount Code Can not be blank", groups = BlankCheck.class)
        private String amount;

        @NotBlank(message = "Unit Code Can not be blank", groups = BlankCheck.class)
        private String unit;

        @NotBlank(message = "Description Can not be blank", groups = BlankCheck.class)
        private String description;

        @NotBlank(message = "Expense type Can not be blank", groups = BlankCheck.class)
        private String expenseType;

        @NotBlank(message = "Currency Can not be blank", groups = BlankCheck.class)
        private String currency;

        @NotBlank(message = "Total Can not be blank", groups = BlankCheck.class)
        private String total;
    }

}
