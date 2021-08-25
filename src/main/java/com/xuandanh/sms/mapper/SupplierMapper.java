package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Staff;
import com.xuandanh.sms.domain.Supplier;
import com.xuandanh.sms.dto.StaffDTO;
import com.xuandanh.sms.dto.SupplierDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class SupplierMapper {
    public List<SupplierDTO> supplierToSupplierDTO(List<Supplier> supplierList) {
        return supplierList.stream().filter(Objects::nonNull).map(this::supplierToSupplierDTO).collect(Collectors.toList());
    }

    public SupplierDTO supplierToSupplierDTO(Supplier supplier){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(supplier,SupplierDTO.class);
    }

    public List<Supplier> supplierDTOToSupplier(List<SupplierDTO> supplierDTOList) {
        return supplierDTOList.stream().filter(Objects::nonNull).map(this::supplierDTOToSupplier).collect(Collectors.toList());
    }

    public Supplier supplierDTOToSupplier(SupplierDTO supplierDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(supplierDTO , Supplier.class);
    }
}
