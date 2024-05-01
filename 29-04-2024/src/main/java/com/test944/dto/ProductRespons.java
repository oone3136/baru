package com.test944.dto;

import com.test944.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRespons {
    private Long id;
    private String name;
    private String description;
    private Long qty;
    private Category category;
}
