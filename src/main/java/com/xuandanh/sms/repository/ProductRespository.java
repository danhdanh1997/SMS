package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Product;
import com.xuandanh.sms.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRespository extends JpaRepository<Product,String> {
    @Query("Select p from Product p where p.productName like %:productName%")
    Page<Product>findAllByProductName(Pageable pageable,String productName);
}
