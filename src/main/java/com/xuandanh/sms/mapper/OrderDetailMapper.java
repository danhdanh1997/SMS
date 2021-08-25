package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Order;
import com.xuandanh.sms.domain.OrderDetails;
import com.xuandanh.sms.dto.OrderDTO;
import com.xuandanh.sms.dto.OrderDetailDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderDetailMapper {
    public List<OrderDetailDTO> orderDetailsToOrderDetailsDTO(List<OrderDetails> orderDetailsList) {
        return orderDetailsList.stream().filter(Objects::nonNull).map(this::orderDetailsToOrderDetailsDTO).collect(Collectors.toList());
    }

    public OrderDetailDTO orderDetailsToOrderDetailsDTO(OrderDetails orderDetails){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderDetails,OrderDetailDTO.class);
    }

    public List<OrderDetails> orderDetailsDTOToOrderDetails(List<OrderDetailDTO> orderDetailDTOList) {
        return orderDetailDTOList.stream().filter(Objects::nonNull).map(this::orderDetailsDTOToOrderDetails).collect(Collectors.toList());
    }

    public OrderDetails orderDetailsDTOToOrderDetails(OrderDetailDTO orderDetailDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderDetailDTO , OrderDetails.class);
    }
}
