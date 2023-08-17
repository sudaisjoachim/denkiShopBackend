package com.denkishop.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;

	public List<Stock> getAllStocks() {
		return stockRepository.findAll();
	}

	public Optional<Stock> getStockById(Long id) {
		return stockRepository.findById(id);
	}

	public Stock saveStock(Stock stock) {
		Date currentDate = new Date();
		stock.setUpdated_at(currentDate);
		stock.setCreated_at(currentDate);
		return stockRepository.save(stock);
	}
	
    public Stock updateStock(Long id, Stock updatedStock) {
        Optional<Stock> existingStock = stockRepository.findById(id);
        if (existingStock.isPresent()) {
            Stock stockToUpdate = existingStock.get();

            if (updatedStock.getProduct() != null) {
                stockToUpdate.setProduct(updatedStock.getProduct());
            }
            if (updatedStock.getQuantity() != null) {
                stockToUpdate.setQuantity(updatedStock.getQuantity());
            }

            stockToUpdate.setUpdated_at(new Date());

            return stockRepository.save(stockToUpdate);
        } else {
            throw new EntityNotFoundException("Stock with ID " + id + " not found");
        }
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
