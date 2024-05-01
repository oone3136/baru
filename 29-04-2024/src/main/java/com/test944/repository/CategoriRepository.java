package com.test944.repository;

import com.test944.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriRepository extends JpaRepository<Category,Long> {
}
