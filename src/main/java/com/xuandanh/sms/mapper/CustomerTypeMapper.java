package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.CustomerType;
import com.xuandanh.sms.domain.Product;
import com.xuandanh.sms.dto.CustomerTypeDTO;
import com.xuandanh.sms.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerTypeMapper {
    public List<CustomerTypeDTO> customerTypeToCustomerTypeDTO(List<CustomerType> customerTypeList) {
        return customerTypeList.stream().filter(Objects::nonNull).map(this::customerTypeToCustomerTypeDTO).collect(Collectors.toList());
    }

    public CustomerTypeDTO customerTypeToCustomerTypeDTO(CustomerType customerType){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customerType,CustomerTypeDTO.class);
    }

    public List<CustomerType> customerTypeDTOToCustomerType(List<CustomerTypeDTO> customerTypeDTOList) {
        return customerTypeDTOList.stream().filter(Objects::nonNull).map(this::customerTypeDTOToCustomerType).collect(Collectors.toList());
    }

    public CustomerType customerTypeDTOToCustomerType(CustomerTypeDTO customerTypeDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customerTypeDTO , CustomerType.class);
    }
}
