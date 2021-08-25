package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Shippers;
import com.xuandanh.sms.domain.Supplier;
import com.xuandanh.sms.dto.ShipperDTO;
import com.xuandanh.sms.dto.SupplierDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class ShipperMapper {
    public List<ShipperDTO> shippersToShippersDTO(List<Shippers> shippersList) {
        return shippersList.stream().filter(Objects::nonNull).map(this::shippersToShippersDTO).collect(Collectors.toList());
    }

    public ShipperDTO shippersToShippersDTO(Shippers shippers){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(shippers,ShipperDTO.class);
    }

    public List<Shippers> shippersDTOToShippers(List<ShipperDTO> shipperDTOList) {
        return shipperDTOList.stream().filter(Objects::nonNull).map(this::shippersDTOToShippers).collect(Collectors.toList());
    }

    public Shippers shippersDTOToShippers(ShipperDTO shipperDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(shipperDTO , Shippers.class);
    }
}
