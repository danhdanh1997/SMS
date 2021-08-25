package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    Page<Supplier>findSupplierBySupplierName(Pageable pageable,String supplierName);
}
