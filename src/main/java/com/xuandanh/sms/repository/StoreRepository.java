package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Integer> {
    Page<Store>findByStoreName(Pageable pageable , String storeName);
}
