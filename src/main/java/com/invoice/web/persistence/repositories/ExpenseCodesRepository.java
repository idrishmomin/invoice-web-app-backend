package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.ExpenseCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCodesRepository extends JpaRepository<ExpenseCodes, Long> {
    ExpenseCodes findByType(String type);
}