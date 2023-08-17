package com.denkishop.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
    // Add custom query methods if needed
}