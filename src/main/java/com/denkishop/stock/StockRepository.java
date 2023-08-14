package com.denkishop.stock;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StockRepository extends JpaRepository<Stock, Long> {
    // You can define additional query methods here if needed
}

