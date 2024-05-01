package com.test944.services;

import com.test944.model.Category;
import com.test944.repository.CategoriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class CategoryService {

    private final CategoriRepository repository;

    public Category createCategori(Category category) {
        return repository.save(category);
    }
    public Category updateCategory(Category category) {
        return repository.save(category);
    }
    public List<Category> getAllCategory(){
        return repository.findAll();
    }
    public Category getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("data {} not found" + id));
    }
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

}
