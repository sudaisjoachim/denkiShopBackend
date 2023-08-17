package com.denkishop.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	public List<Sale> getAllSales() {
		return saleRepository.findAll();
	}

	public Optional<Sale> getSaleById(Long id) {
		return saleRepository.findById(id);
	}

	public Sale saveSale(Sale sale) {
		Date currentDate = new Date();
		sale.setUpdated_at(currentDate);
		sale.setCreated_at(currentDate);
		return saleRepository.save(sale);
	}

	public Sale updateSale(Optional<Sale> existingSale, Sale updatedSaleData) {
		Date currentDate = new Date();
		existingSale.get().setUpdated_at(currentDate);
		// Update specific fields based on the provided data
		if (updatedSaleData.getName() != null) {
			existingSale.get().setName(updatedSaleData.getName());
		}
		if (updatedSaleData.getPrice() != null) {
			existingSale.get().setPrice(updatedSaleData.getPrice());
		}
		if (updatedSaleData.getQuantity() != null) {
			existingSale.get().setQuantity(updatedSaleData.getQuantity());
		}
		if (updatedSaleData.getCost() != null) {
			existingSale.get().setCost(updatedSaleData.getCost());
		}
		if (updatedSaleData.getSalePrice() != null) {
			existingSale.get().setSalePrice(updatedSaleData.getSalePrice());
		}
		// Save the updated sale
		return saleRepository.save(existingSale.get());
	}

	public void deleteSale(Long id) {
		if (saleRepository.existsById(id)) {
			saleRepository.deleteById(id);
		} else {
			// Handle case when the entity does not exist
			throw new EntityNotFoundException("Sale with ID " + id + " not found");
		}
	}

}
