package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.ExpenseCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseCodesRepository extends JpaRepository<ExpenseCodes, Long> {
    @Query("select e from ExpenseCodes e where e.expenseCode = ?1")
    Optional<ExpenseCodes> findByExpenseCode(String expenseName);
}