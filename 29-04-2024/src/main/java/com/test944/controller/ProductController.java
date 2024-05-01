package com.test944.controller;

import com.test944.dto.ProductRequest;
import com.test944.dto.ProductRespons;
import com.test944.dto.UpdateProductRequest;
import com.test944.model.Product;
import com.test944.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService service;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        try {
            service.createProduct(productRequest);
            return ResponseEntity.ok("Product berhasil dibuat");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return service.getProductById(id);
    }
    @PutMapping("/product/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestBody UpdateProductRequest productRequest){
        try {
            service.updateProduct(id, productRequest);
            return ResponseEntity.ok("Product berhasil diupdate");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/get/product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> getAllProducts(String search, long limit, long offset){
        return service.getAllProduct(search,limit,offset);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
    }
}
