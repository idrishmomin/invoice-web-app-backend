package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("select v from Vendor v where v.vendorId = ?1")
    Optional<Vendor> findByVendorId(String vendorId);

    @Query("select v from Vendor v where v.vendorName = ?1")
    Optional<Vendor> findByVendorName(String vendorName);
}