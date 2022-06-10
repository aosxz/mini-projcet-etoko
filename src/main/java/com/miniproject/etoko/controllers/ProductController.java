package com.miniproject.etoko.controllers;

import com.miniproject.etoko.dtos.RestResponse;
import com.miniproject.etoko.dtos.product.ProductHeaderDTO;
import com.miniproject.etoko.dtos.product.UpsertProductDTO;
import com.miniproject.etoko.services.ProductService;
import com.miniproject.etoko.services.ProductServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("find-all")
    public ResponseEntity<RestResponse<List<ProductHeaderDTO>>> findAllProduct(){
        return new ResponseEntity<>(
                new RestResponse<>(productService.findAllProduct(),
                        "Product berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }
    @GetMapping("find-by-id/{productId}")
    public ResponseEntity<RestResponse<ProductHeaderDTO>> findProductById(@PathVariable String productId){
        return new ResponseEntity(
                new RestResponse<>(productService.findProductById(productId),
                        "Product dengan ID " + productId + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity<RestResponse<ProductHeaderDTO>> insertNewProduct (@RequestBody UpsertProductDTO newProduct){
        return new ResponseEntity(
                new RestResponse<>(productService.insertProduct(newProduct),
                        "Product berhasil ditambahkan",
                        201),
                HttpStatus.CREATED);
    }

    @PutMapping("{productId}")
    public ResponseEntity<RestResponse> updateProduct(@PathVariable String productId, @RequestBody UpsertProductDTO productUpdateDTO){

        return new ResponseEntity(
                new RestResponse(productService.updateProduct(productId, productUpdateDTO),
                        "Product ID"+productId+" berhasil diupdate",
                        201),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<RestResponse> deleteProduct(@PathVariable String productId){
        return new ResponseEntity(
                new RestResponse(productService.deleteProduct(productId),
                        "Product ID " + productId + " berhasil dihapus ",
                        201),
                HttpStatus.OK);
    }

}
