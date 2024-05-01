package com.test944.repository;

import com.test944.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNama(String cekNama);

    @Query(value = "SELECT * FROM product WHERE (:nama IS NULL OR nama LIKE %:nama%) ORDER BY id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Product> findProductData(@Param("nama") String search,
                                  @Param("limit") long limit,
                                  @Param("offset") long offset);
}
