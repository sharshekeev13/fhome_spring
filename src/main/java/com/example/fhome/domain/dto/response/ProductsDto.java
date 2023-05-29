package com.example.fhome.domain.dto.response;


import com.example.fhome.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductsDto {

    int totalPages;
    List<Product> products;

}
