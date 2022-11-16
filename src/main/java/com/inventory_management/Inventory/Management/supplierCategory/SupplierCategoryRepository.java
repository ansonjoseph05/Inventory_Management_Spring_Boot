package com.inventory_management.Inventory.Management.supplierCategory;

import com.inventory_management.Inventory.Management.entity.SupplierCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierCategoryRepository extends JpaRepository<SupplierCategory,Long> {

    Optional<SupplierCategory> findBySupplierCategoryNameContaining(String supplierCategoryName);
}