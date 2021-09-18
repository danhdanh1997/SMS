package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Cart;
import com.xuandanh.sms.domain.Customer;
import com.xuandanh.sms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findAllByCustomerOrderByCreatedDate(Customer customer);
    List<Cart> deleteByCustomer(Customer customer);

    void deleteById(String customerId);
}
