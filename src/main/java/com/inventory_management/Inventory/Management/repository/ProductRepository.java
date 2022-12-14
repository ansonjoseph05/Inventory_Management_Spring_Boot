package com.inventory_management.Inventory.Management.repository;

import com.inventory_management.Inventory.Management.entity.Product;
import com.inventory_management.Inventory.Management.entity.ProductSellingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    public List<Product> findByProductCode(Long productCode);

    public List<Product> findByProductNameContaining(String productName);


    @Query(
            value = "select * from product s where s.category_id=?1",
            nativeQuery = true
    )
    public List<Product> findProductByCategoryId(Long categoryId);


    @Query(
            value = "select * from product s where s.category_id=?1 and s.product_id=?2",
            nativeQuery = true
    )

    public List<Product> findProductIdByCategoryId(Long categoryId, Long productId);

    @Query(
            value = "select * from product s where s.category_id=?1 and s.product_id=?2",
            nativeQuery = true
    )

    public Product findProductIdUsingCategoryId(Long categoryId, Long productId);

}
