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

    @Valid
    private VendorDetails vendorDetails;

    @Valid
    private Total total;

    @Valid
    private AccountDue accountDetails;

    @Valid
    private Submitter submitter;

    @Valid
    private List<Item> items;

    private String invoiceStatus;
    private String createdBy;
    private String updatedBy;



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
        private String grandTotal;    // subTotal + adjustments
    }


    // AccountDue class
    @Setter
    @Getter
    @NoArgsConstructor
    @GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, AccountDue.class})
    public static class AccountDue {

        @NotBlank(message = "Account Type Can not be blank", groups = BlankCheck.class)
        private String accountType;

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

    @Setter
    @Getter
    @NoArgsConstructor
    @GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, VendorDetails.class})
    public static class VendorDetails {

        @NotBlank(message = "BillTo Can not be blank", groups = BlankCheck.class)
        private String billTo;

        @NotBlank(message = "Payment Due Can not be blank", groups = BlankCheck.class)
        private String paymentDue;

        @NotBlank(message = "Vendor Bank Details Can not be blank", groups = BlankCheck.class)
        private String vendorBankDetails;
    }

    // Item class
    @Setter
    @Getter
    @NoArgsConstructor
    @GroupSequence({BlankCheck.class, NoNullStringCheck.class, PatternCheck.class, LengthCheck.class, Item.class})
    public static class Item {

        @NotBlank(message = "Vendor Invoice Ref Can not be blank", groups = BlankCheck.class)
        private String vendorInvoiceRef;

        @NotBlank(message = "Vendor Id Can not be blank", groups = BlankCheck.class)
        private String vendorId;

        @NotBlank(message = "Vendor Name Can not be blank", groups = BlankCheck.class)
        private String vendorName;

        @NotBlank(message = "VendorInvoiceDate Can not be blank", groups = BlankCheck.class)
        private String vendorInvoiceDate;

        @NotBlank(message = "Cost Code Can not be blank", groups = BlankCheck.class)
        private String costCode;

        @NotBlank(message = "Expense type Can not be blank", groups = BlankCheck.class)
        private String expenseType;

        @NotBlank(message = "Description Can not be blank", groups = BlankCheck.class)
        private String description;

        @NotBlank(message = "Description Can not be blank", groups = BlankCheck.class)
        private String rateOfSAR;

        @NotBlank(message = "Currency Can not be blank", groups = BlankCheck.class)
        private String currency;


        private String invoiceAmount;   // don't show in petty cash
        private String recurring;    // don't show in petty cash


        // petty Cash fields
        private String itemAmount;
        private String quantity;
        private String subTotal;   // itemAmount * quantity * rateOfSAR
        private String ptcAdvance;
        private String total;    // subTotal + ptcAdvance

    }

}
