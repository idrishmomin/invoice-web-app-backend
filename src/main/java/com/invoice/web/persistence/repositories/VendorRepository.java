package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    @Query("select v from Vendor v where v.vendorId = ?1")
    Vendor findByVendorId(String vendorId);
}