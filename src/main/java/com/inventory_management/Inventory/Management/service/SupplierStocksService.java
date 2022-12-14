package com.inventory_management.Inventory.Management.service;

import com.inventory_management.Inventory.Management.dto.SupplierCategorySupplierStockDTO;
import com.inventory_management.Inventory.Management.entity.Message;
import com.inventory_management.Inventory.Management.entity.SupplierStocks;
import com.inventory_management.Inventory.Management.error.NotFoundException;

import java.util.List;

public interface SupplierStocksService {
    SupplierStocks saveSupplierStocks(SupplierStocks supplierStocks, Long supplierCategoryId) throws NotFoundException;


    List<SupplierCategorySupplierStockDTO> getAllProducts();

    List<SupplierCategorySupplierStockDTO> fetchAllProductsByCategory(Long supplierCategoryId) throws NotFoundException;

    List<SupplierCategorySupplierStockDTO> getBySupplierCategoryIdAndSupplierStocksId(Long supplierCategoryId, Long supplierStocksId) throws NotFoundException;

    List<SupplierCategorySupplierStockDTO> searchBySupplierProductName(String supplierProductName) throws NotFoundException;

    Message deleteSupplierProduct(Long supplierStocksId) throws NotFoundException;

    List<SupplierCategorySupplierStockDTO> getProductsById(Long supplierStocksId) throws NotFoundException;


    Message updateSupplierProduct(Long supplierCategoryId, Long supplierStocksId, SupplierStocks supplierStocks) throws NotFoundException;
}
