package com.miniproject.etoko.dtos.product;

import com.miniproject.etoko.models.Product;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class ProductHeaderDTO implements Serializable {
    private final String productId;
    private final String nameProduct;
    private final String category;
    private final String price;
    private final String description;
    private final Integer stock;

    public static ProductHeaderDTO set(Product product){
        //create format currency
        Locale locale = new Locale("id","ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        return new ProductHeaderDTO(
                product.getProductId(),
                product.getNameProduct(),
                product.getCategory(),
                formatDuit.format(product.getPrice()),
                product.getDescription(),
                product.getStock());
    }
    public static List<ProductHeaderDTO> toList(List<Product> products){
        List<ProductHeaderDTO> result = new ArrayList<>();
        for (Product product :products){
            result.add(set(product));
        }
        return result;
    }
}
