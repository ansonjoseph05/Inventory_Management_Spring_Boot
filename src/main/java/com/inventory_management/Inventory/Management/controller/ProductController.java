package com.inventory_management.Inventory.Management.controller;


import com.inventory_management.Inventory.Management.service.ProductService;
import com.inventory_management.Inventory.Management.repository.CategoryRepository;
import com.inventory_management.Inventory.Management.service.ProductSellingPriceService;
import com.inventory_management.Inventory.Management.dto.CategoryProductDTO;
import com.inventory_management.Inventory.Management.entity.Product;
import com.inventory_management.Inventory.Management.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSellingPriceService productSellingPriceService;

    // Add Products to Category

    @PostMapping("/category/{categoryId}/addProduct")
    public Product saveProduct(@RequestBody Product product,
                               @PathVariable Long categoryId) {
        return productService.saveProduct(product, categoryId);
    }


    // Get all products with categories

    @GetMapping("/product")
    public List<CategoryProductDTO> fetchProductList() {
        return productService.fetchProductList();
    }


    // Get products from a specific category

    @GetMapping("/category/{categoryId}/product")
    public List<CategoryProductDTO> fetchProductsByCategoryId(@PathVariable Long categoryId) throws NotFoundException {
        return productService.fetchProductsByCategoryId(categoryId);
    }

    // Get a specific product from a specific category


    @GetMapping("/category/{categoryId}/product/{productId}")
    public List<CategoryProductDTO> fetchProductIdByCategoryId(@PathVariable Long categoryId,
                                                               @PathVariable Long productId) throws NotFoundException {
        return productService.fetchProductIdByCategoryId(categoryId, productId);

    }


    // Get product by product code (unique and String type)


    @GetMapping("/product/productCode/{productCode}")
    public List<CategoryProductDTO> fetchByProductCode(@PathVariable Long productCode) throws NotFoundException {
        return productService.fetchByProductCode(productCode);
    }


    // Get product by product name (containing)


    @GetMapping("/product/{productName}")
    public List<CategoryProductDTO> fetchByProductName(@PathVariable String productName) throws NotFoundException {
        return productService.fetchByProductName(productName);
    }

    // Get a product by productId

    @GetMapping("/product/productId/{productId}")
    public List<CategoryProductDTO> fetchByProductId(@PathVariable Long productId) throws NotFoundException {
        return productService.fetchByProductId(productId);
    }


    // Update a specific product in a specific category


    @PutMapping("/category/{categoryId}/updateProduct/{productId}")
    public String updateProduct(@PathVariable Long categoryId,
                                @PathVariable Long productId,
                                @RequestBody Product product) {
        return productService.updateProduct(categoryId, productId, product);
    }


    // Delete a product


    @DeleteMapping("/product/delete/{productId}")
    public String deleteProductById(@PathVariable Long productId) throws NotFoundException {
        productService.deleteProduct(productId);
        return "Product Deleted !";
    }
}
