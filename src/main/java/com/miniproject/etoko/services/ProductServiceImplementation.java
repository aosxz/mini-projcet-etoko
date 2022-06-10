package com.miniproject.etoko.services;

import com.miniproject.etoko.dtos.product.ProductHeaderDTO;
import com.miniproject.etoko.dtos.product.UpsertProductDTO;
import com.miniproject.etoko.models.Product;
import com.miniproject.etoko.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation  implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<ProductHeaderDTO> findAllProduct() {
        Locale locale = new Locale("id","ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        List<Product> productList = productRepository.findAll();
        List<ProductHeaderDTO> productStream = productList.stream()
                .map(product -> new ProductHeaderDTO(
                                product.getProductId(),
                                product.getNameProduct(),
                                product.getCategory(),
                                formatDuit.format(product.getPrice()),
                                product.getDescription(),
                                product.getStock()
                        )
                ).collect(Collectors.toList());

        return productStream;
    }

    @Override
    public List<ProductHeaderDTO> findProductById(String productId) {
        Optional<Product> productList = Optional.ofNullable(productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(" Data tidak ditemukan")));
        Locale locale = new Locale("id","ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        List<ProductHeaderDTO> productStream = productList.stream()
                .map(product -> new ProductHeaderDTO(
                        product.getProductId(),
                        product.getNameProduct(),
                        product.getCategory(),
                        formatDuit.format(product.getPrice()),
                        product.getDescription(),
                        product.getStock()
                )).collect(Collectors.toList());
        return productStream;
    }

    @Override
    public List<ProductHeaderDTO> insertProduct(UpsertProductDTO newProduct) {
        Product product = newProduct.convert();
        productRepository.save(product);
        return ProductHeaderDTO.toList(productRepository.findAll());
    }


    @Override
    public boolean updateProduct(String productId, UpsertProductDTO productUpdateDTO) {
        productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Tidak bisa update karena productID "+ productId +" Tidak ada ! "));
        boolean result = false;
        Product oldProduct = productRepository.findById(productId).orElse(null);

        oldProduct.setPrice(productUpdateDTO.getPrice()== null ? oldProduct.getPrice() : productUpdateDTO.getPrice());
        oldProduct.setDescription(productUpdateDTO.getDescription() == null ? oldProduct.getDescription() : productUpdateDTO.getDescription());
        oldProduct.setStock(productUpdateDTO.getStock() == null ? oldProduct.getStock() : productUpdateDTO.getStock());

        productRepository.save(oldProduct);
        return !result;
    }


    @Override
    public boolean deleteProduct(String productId) {
        productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product dengan " +productId+ " Tidak ada di database"));
        productRepository.deleteById(productId);
        return true;
    }


}
