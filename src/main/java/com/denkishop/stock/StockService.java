package com.denkishop.stock;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

	private final StockRepository stockRepository;

	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public List<Stock> getAllStocks() {
		return stockRepository.findAll();
	}

	public Optional<Stock> getStockById(Long id) {
		return stockRepository.findById(id);
	}

	public Stock saveStock(Stock stock) {
		stock.setCreated_at(new Date());
		stock.setUpdated_at(new Date());
		return stockRepository.save(stock);
	}

	public void deleteStock(Long id) {
	      if (stockRepository.existsById(id)) {
	    	  stockRepository.deleteById(id);
	        } else {
	            // Handle case when the entity does not exist
	            throw new EntityNotFoundException("Stock with ID " + id + " not found");
	        }
	}

}
