package com.denkishop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.denkishop.sales.Sale;
import com.denkishop.sales.SaleRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	
	@Autowired
	SaleRepository saleRepository ;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public Product addProduct(Product product) {
        product.setCreated_at(new Date()); 
        product.setUpdated_at(new Date());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {

        Product existingProduct = getProductById(id);
        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getPrice() != null && product.getPrice() > 0) {
            existingProduct.setPrice(product.getPrice());
        }
        existingProduct.setUpdated_at(new Date());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        // Check if there are associated sales records
        List<Sale> associatedSales = saleRepository.findByProductId(id);

        if (associatedSales.isEmpty()) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cannot delete product with associated sales records");
            // Alternatively, you can return a custom error response or perform a different action
        }
    }
}
