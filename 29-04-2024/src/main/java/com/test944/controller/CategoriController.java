package com.test944.controller;

import com.test944.model.Category;
import com.test944.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoriController {
    private final CategoryService service;

    @GetMapping("/get/category")
    public List<Category> getAllCategory(){
        return service.getAllCategory();
    }
    @GetMapping("/category/{id}")
    public Category getById(@PathVariable("id") Long id)     {
        return service.getById(id);
    }
    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        try {
            service.createCategori(category);
            return ResponseEntity.ok("Category berhasil dibuat");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/category/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateCategory(@PathVariable("id") @RequestBody Category category){
        try {
            service.updateCategory(category);
            return ResponseEntity.ok("Category berhasil diupdate");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        service.deleteCategory(id);
    }
}
