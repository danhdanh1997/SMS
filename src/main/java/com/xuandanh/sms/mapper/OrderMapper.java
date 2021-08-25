package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Category;
import com.xuandanh.sms.domain.Order;
import com.xuandanh.sms.dto.CategoryDTO;
import com.xuandanh.sms.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public List<OrderDTO> orderToOrderDTO(List<Order> orderList) {
        return orderList.stream().filter(Objects::nonNull).map(this::orderToOrderDTO).collect(Collectors.toList());
    }

    public OrderDTO orderToOrderDTO(Order order){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(order,OrderDTO.class);
    }

    public List<Order> orderDTOToOrder(List<OrderDTO> orderDTOList) {
        return orderDTOList.stream().filter(Objects::nonNull).map(this::orderDTOToOrder).collect(Collectors.toList());
    }

    public Order orderDTOToOrder(OrderDTO orderDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderDTO , Order.class);
    }
}
