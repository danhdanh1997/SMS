package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetails,String> {
}
