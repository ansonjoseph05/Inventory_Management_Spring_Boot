package com.inventory_management.Inventory.Management.controller;

import com.inventory_management.Inventory.Management.entity.Message;
import com.inventory_management.Inventory.Management.service.SupplierService;
import com.inventory_management.Inventory.Management.entity.Supplier;
import com.inventory_management.Inventory.Management.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Get all suppliers

    @GetMapping("/supplier")
    public List<Supplier> fetchSupplierList() {
        return supplierService.fetchSupplierList();
    }

    // Get supplier By Id

    @GetMapping("/supplier/{supplierId}")
    public Optional<Supplier> fetchSupplierById(@PathVariable("supplierId") Long supplierId) throws NotFoundException {
        return supplierService.fetchSupplierById(supplierId);
    }


    // Get supplier By Name

    @GetMapping("/supplier/supplierName/{supplierName}")
    public List<Supplier> fetchSupplierByName(@PathVariable("supplierName") String supplierName) throws NotFoundException {
        return supplierService.fetchSupplierByName(supplierName);
    }

    // Get supplier By Company

    @GetMapping("/supplier/supplierCompany/{supplierCompany}")
    public List<Supplier> fetchSupplierByCompanyName(@PathVariable("supplierCompany") String supplierCompany) throws NotFoundException {
        return supplierService.fetchSupplierByCompanyName(supplierCompany);
    }

    // update supplier

    @PutMapping("/supplier/updateSupplier/{supplierId}")
    public Message updateSupplier(@PathVariable("supplierId")Long supplierId, @RequestBody Supplier supplier) throws NotFoundException{
        return supplierService.updateSupplier(supplierId , supplier);
    }

}
