package com.denkishop.sales;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SaleRepository extends JpaRepository<Sale, Long> {
	 @Query("SELECT s FROM Sale s WHERE s.product.id = ?1")
	    List<Sale> findByProductId(Long productId);
}
