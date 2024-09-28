package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.Submitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitterRepository extends JpaRepository<Submitter, Long> {
}