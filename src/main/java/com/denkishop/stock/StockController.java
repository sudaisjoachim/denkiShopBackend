package com.denkishop.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.denkishop.product.Product;
import com.denkishop.product.ProductService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stocks")
public class StockController {

   

    @Autowired
    ProductService productService;
    
    @Autowired
    private StockService stockService;
  

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Long productId = stock.getProduct().getId();
        Product product = productService.getProductById(productId); // Fetch the associated product
        stock.setProduct(product); // Set the fetched product in the stock

        Stock savedStock = stockService.saveStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

}

