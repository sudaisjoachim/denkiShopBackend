package com.denkishop.stock;

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
@RequestMapping("/stocks")
public class StockController {

   

    @Autowired
    private ProductService productService;
    
    @Autowired
    private StockService stockService;
  
    @GetMapping("/all")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        
        if (stocks.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Long productId = stock.getProduct().getId();
        Product product = productService.getProductById(productId); // Fetch the associated product
        stock.setProduct(product); // Set the fetched product in the stock

        Stock savedStock = stockService.saveStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ResourceResponse<Stock>> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStock(id, stock);
        String message = "Stock with ID " + id + " updated successfully!";
        ResourceResponse<Stock> resourceResponse = new ResourceResponse<>(updatedStock, message);
        return ResponseEntity.ok(resourceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

}

