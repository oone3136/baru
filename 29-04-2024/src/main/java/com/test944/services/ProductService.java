package com.test944.services;

import com.test944.dto.ProductRequest;
import com.test944.dto.UpdateProductRequest;
import com.test944.model.Category;
import com.test944.model.Product;
import com.test944.repository.CategoriRepository;
import com.test944.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service @RequiredArgsConstructor @Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final CategoriRepository categoriRepository;

    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("{} ini tidak ada" + id));
    }
    public ResponseEntity<Map<String, Object>> getAllProduct(String search, long limit, long offset) {
        try {
            if (limit == -99) {
                limit = repository.count();
            }

            if (offset == -99) {
                offset = 0;
            }

            if (search == null) {
                search = "";
            }

            Map<String, Object> items = new HashMap<>();
            List<Product> products = repository.findProductData(search, limit, offset);

            if (products.isEmpty()) {
                throw new RuntimeException("Data Not Found");
            }

            items.put("items", products);
            items.put("totalDataResult", products.size());

            return ResponseEntity.ok(items);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    public void createProduct(ProductRequest productRequest) {
        String cekNama = productRequest.getNama();
        if (cekNama.length() < 1) {
            throw new RuntimeException("nama tidak boleh kosong");
        }
        if (repository.existsByNama(cekNama)) {
            throw new RuntimeException("Nama produk sudah ada");
        }
        Long id = productRequest.getCategory();
        Optional<Category> categoryOptional = categoriRepository.findById(id);
        if (categoryOptional.isPresent()){
            Product product = Product.builder()
                    .nama(cekNama)
                    .description(productRequest.getDescription())
                    .qty(productRequest.getQty())
                    .category(categoryOptional.get())
                    .build();
            repository.save(product);
            log.info("product {} is saved" + product.getId());
        }else {
            throw new RuntimeException("Category not found");
        }
    }
    public void updateProduct(Long id, UpdateProductRequest productRequest) {
        String cekNama = productRequest.getNama();
        if (cekNama.length() < 1) {
            throw new RuntimeException("nama tidak boleh kosong");
        }
        Long category = productRequest.getCategory();
        Optional<Category> categoryOptional = categoriRepository.findById(category);
        if (categoryOptional.isPresent()){
            Product product = Product.builder()
                    .id(productRequest.getId())
                    .nama(cekNama)
                    .description(productRequest.getDescription())
                    .qty(productRequest.getQty())
                    .category(categoryOptional.get())
                    .build();
            repository.save(product);
            log.info("product {} is updated" + product.getId());
        }else {
            throw new RuntimeException("Category not found");
        }
    }

    private Product mapToProductResponse (Product product){
        return Product.builder()
                .id(product.getId())
                .nama(product.getNama())
                .description(product.getDescription())
                .qty(product.getQty())
                .category(product.getCategory())
                .build();
    }


    public void deleteProduct (Long id){
        repository.deleteById(id);
    }
}
