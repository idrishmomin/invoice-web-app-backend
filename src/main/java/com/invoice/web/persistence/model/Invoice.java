package com.invoice.web.persistence.model;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "invoice_number", unique = true, nullable = false)
    private String invoiceNumber;

    @Column(name = "rate_of_SAR")
    private String rateOfSAR;

    @Column(name = "vendor_invoice_Date")
    private String vendorInvoiceDate;

    private LocalDateTime invoiceCreatedDate;

    private LocalDateTime invoiceUpdatedDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.Submitter editedBy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.Total total;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.BankDetails bankDetails;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.AccountDue accountDue;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.Submitter submitter;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<CreateInvoiceRequest.Item> items;
}
