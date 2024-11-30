package com.invoice.web.persistence.repositories;

import com.invoice.web.persistence.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    SystemConfig findBySystemKey(String systemKey);


}