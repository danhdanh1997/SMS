package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.OrderDetails;
import com.xuandanh.sms.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public void addOrderItem(OrderDetails orderDetails){
        orderDetailRepository.save(orderDetails);
    }
}
