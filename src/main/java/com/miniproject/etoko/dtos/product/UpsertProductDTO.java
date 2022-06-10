package com.miniproject.etoko.dtos.product;

import com.miniproject.etoko.models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpsertProductDTO {
    private  String productId;
    private  String nameProduct;
    private  String category;
    private BigDecimal price;
    private  String description;
    private  Integer stock;


    public Product convert() {
        return new Product(
                productId,
                nameProduct,
                category,
                price,
                description,
                stock);
    }




}
