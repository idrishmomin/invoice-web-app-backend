package com.invoice.web.api.dto.request;

import com.invoice.web.infrastructure.utils.validation.BlankCheck;
import com.invoice.web.infrastructure.utils.validation.LengthCheck;
import com.invoice.web.infrastructure.utils.validation.NoNullStringCheck;
import com.invoice.web.infrastructure.utils.validation.PatternCheck;
import jakarta.validation.GroupSequence;
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
    private String vendorInvoiceDate;
    private Total total;
    private AccountDue accountDue;
    private Submitter submitter;
    private Submitter updatedBy;
    private List<Item> items;


    // SubTotal class
    @Setter
    @Getter
    @NoArgsConstructor
    public static class Total {
        private String subTotal;
        private String adjustments;
        private String grandTotal;
    }


    // AccountDue class
    @Setter
    @Getter
    @NoArgsConstructor
    public static class AccountDue {
        private String accountType;
        private String totalDue;
        private String paymentType;
    }

    // Submitter class
    @Setter
    @Getter
    @NoArgsConstructor
    public static class Submitter {
        private String submitterName;
        private String department;
    }

    // Item class
    @Setter
    @Getter
    @NoArgsConstructor
    public static class     Item {
        private String refId;
        private String vendorInvoiceRef;
        private String rateOfSAR;
        private String vendorId;
        private String costCode;
        private String expenseCode;
        private String quantity;
        private String amount;
        private String unit;
        private String description;
        private String expenseType;
        private String currency;
        private String total;
    }

}
