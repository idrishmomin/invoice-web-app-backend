package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {
    List<CostCenter> findByName(String name);
}