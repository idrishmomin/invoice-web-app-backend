package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findByInvoiceNumber(String invoiceNumber);
    @Query("SELECT i FROM Invoice i  where i.deleted = false ORDER BY i.invoiceUpdatedDate DESC")
    Page<Invoice> findAllInvoicesOrderedByCreatedDateDesc(Pageable pageable);

    @Query(value = """
    SELECT * FROM Invoice i
    WHERE (COALESCE(?1, '') = '' OR i.invoice_number LIKE CONCAT('%', ?1, '%'))
    AND (COALESCE(?2, '') = '' OR JSON_UNQUOTE(JSON_EXTRACT(i.vendor_details, '$.billTo')) LIKE CONCAT('%', ?2, '%'))
    AND (COALESCE(?3, '') = '' OR i.invoice_status LIKE CONCAT('%', ?3, '%'))
    AND i.deleted = false
    ORDER BY i.invoice_updated_date DESC
    """, nativeQuery = true)
    Page<Invoice> findInvoiceByFilteredValues(
            @Nullable String invoiceNumber,
            @Nullable String vendorName,
            @Nullable String invoiceStatus,
            Pageable pageable);


    @Query("select i from Invoice i where i.createdBy = ?1 AND  i.deleted = false order by i.invoiceUpdatedDate DESC")
    Page<Invoice> findByCreatedByOrderByInvoiceCreatedDateDesc(String createdBy,Pageable pageable);

    @Query(value = "SELECT * FROM invoice i WHERE JSON_CONTAINS(i.items, JSON_OBJECT('vendorInvoiceRef', :vendorInvoiceRef))  LIMIT 1", nativeQuery = true)
    List<Invoice> findByVendorInvoiceRef(@Param("vendorInvoiceRef") String vendorInvoiceRef);


}