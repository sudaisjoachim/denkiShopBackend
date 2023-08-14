package com.denkishop.sales;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

  
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale saveSale(Sale sale) {
    	  Date currentDate = new Date();
          
          if (sale.getId() == null) {
              sale.setCreated_at(currentDate);
          }

          sale.setUpdated_at(currentDate);
          
          return saleRepository.save(sale);
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

