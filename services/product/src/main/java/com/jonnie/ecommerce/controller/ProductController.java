package com.jonnie.ecommerce.controller;


import com.jonnie.ecommerce.product.ProductPurchaseRequest;
import com.jonnie.ecommerce.product.ProductPurchaseResponse;
import com.jonnie.ecommerce.product.ProductRequest;
import com.jonnie.ecommerce.product.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //method to create a product
    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest productRequest
    ){
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    //method to purchase products
    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody @Valid List<ProductPurchaseRequest> productPurchaseRequests
    ){
        return ResponseEntity.ok(productService.purchaseProducts(productPurchaseRequests));
    }

    //method to get a  product
    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable("product-id") Integer productId
    ){
        return ResponseEntity.ok(productService.findById(productId));
    }

    //method to get all products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.findAll());
    }


}
