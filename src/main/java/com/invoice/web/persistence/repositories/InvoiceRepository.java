package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findByInvoiceNumber(String invoiceNumber);
    @Query("SELECT i FROM Invoice i ORDER BY i.invoiceCreatedDate DESC")
    List<Invoice> findAllInvoicesOrderedByCreatedDateDesc();

    @Query("select i from Invoice i where i.createdBy = ?1 order by i.invoiceCreatedDate DESC")
    List<Invoice> findByCreatedByOrderByInvoiceCreatedDateDesc(String createdBy);


}