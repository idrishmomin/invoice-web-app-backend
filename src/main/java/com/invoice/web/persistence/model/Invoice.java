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

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    private String createdBy;

    private String updatedBy;

    private String invoiceStatus;

    private LocalDateTime invoiceCreatedDate;

    private LocalDateTime invoiceUpdatedDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.Total total;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.AccountDue accountDetails;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.VendorDetails vendorDetails;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private CreateInvoiceRequest.Submitter submitter;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<CreateInvoiceRequest.Item> items;

    private boolean deleted;
}
