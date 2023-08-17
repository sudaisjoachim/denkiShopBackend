package com.denkishop.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.denkishop.product.Product;
import com.denkishop.product.ProductService;
import com.denkishop.utils.ResourceResponse;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sales")
public class SaleController {

	@Autowired
	ProductService productService;

	@Autowired
	private SaleService saleService;

	@GetMapping("/all")
	public List<Sale> getAllSales() {
		return saleService.getAllSales();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
		Optional<Sale> sale = saleService.getSaleById(id);
		return sale.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/new")
	public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
		Long productId = sale.getProduct().getId();
		Product product = productService.getProductById(productId); // Fetch the associated product
		sale.setProduct(product); // Set the fetched product in the sale

		Sale savedSale = saleService.saveSale(sale);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ResourceResponse<Sale>> updateSale(@PathVariable Long id, @RequestBody Sale sale) {
	    Optional<Sale> existingSale = saleService.getSaleById(id);
	    if (existingSale.isPresent()) {
	        Sale updatedSale = saleService.updateSale(existingSale, sale);
	        String message = "Sale with ID " + id + " updated successfully!";
	        ResourceResponse<Sale> resourceResponse = new ResourceResponse<>(updatedSale, message);
	        return ResponseEntity.ok(resourceResponse);
	    } else {
	        String errorMessage = "Associated Sale id : " + id + " not found!";
	        ResourceResponse<Sale> errorResponse = new ResourceResponse<>(null, errorMessage);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    }
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
		saleService.deleteSale(id);
		return ResponseEntity.noContent().build();
	}

}
