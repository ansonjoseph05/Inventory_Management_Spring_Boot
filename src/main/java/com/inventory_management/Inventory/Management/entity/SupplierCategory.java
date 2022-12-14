package com.inventory_management.Inventory.Management.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierCategoryId;

    @Column(name = "supplier_category_name")
    private String supplierCategoryName;

    @CreationTimestamp
    @Column(name = "supplier_category_created_date_time")
    private Date supplierCategoryCreatedDateTime;

}
