package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.Currencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies, Integer> {
}