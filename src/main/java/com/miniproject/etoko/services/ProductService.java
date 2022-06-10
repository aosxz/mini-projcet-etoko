package com.miniproject.etoko.services;

import com.miniproject.etoko.dtos.product.ProductHeaderDTO;
import com.miniproject.etoko.dtos.product.UpsertProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductHeaderDTO> findAllProduct();

    List<ProductHeaderDTO> findProductById(String productId);

    List<ProductHeaderDTO> insertProduct(UpsertProductDTO newProduct);

    boolean updateProduct(String productId, UpsertProductDTO productUpdateDTO);

    boolean deleteProduct(String productId);
}
