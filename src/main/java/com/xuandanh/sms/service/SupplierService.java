package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Supplier;
import com.xuandanh.sms.dto.SupplierDTO;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.SupplierMapper;
import com.xuandanh.sms.repository.CityRepository;
import com.xuandanh.sms.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final CityRepository cityRepository;

    public Optional<SupplierDTO>findOne(int supplierId){
        return Optional.ofNullable(supplierMapper
                .supplierToSupplierDTO(supplierRepository
                .findById(supplierId)
                .orElseThrow(()-> new ResourceNotFoundException("supplier with id:"+supplierId+" not exist"))));
    }

    public Optional<List<SupplierDTO>>findAll(){
        return Optional.of(supplierMapper.supplierToSupplierDTO(supplierRepository.findAll()));
    }

    public Optional<SupplierDTO>createSupplier(int citiesId, Supplier supplier){
        return Optional.ofNullable(cityRepository
                .findById(citiesId)
                .map(city -> {
                    supplier.setCitiesId(city.getCitiesId());
                    return supplierMapper.supplierToSupplierDTO(supplierRepository.save(supplier));
                }).orElseThrow(()->new ResourceNotFoundException("city with id:"+citiesId+" not exist")));
    }

    public Optional<SupplierDTO>updateSupplier(int citiesId,Supplier supplier){
        return Optional.ofNullable(Optional.of(cityRepository
                .findById(citiesId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(city -> {
                    supplierRepository.findById(supplier.getSuppliersId())
                            .map(supplier1 -> {
                                supplier1.setSupplierName(supplier.getSupplierName());
                                supplier1.setCitiesId(city.getCitiesId());
                                supplier1.setPhone(supplier.getPhone());
                                supplier1.setWebsiteUrl(supplier.getWebsiteUrl());
                                supplier1.setProducts(supplier.getProducts());
                                return supplierMapper.supplierToSupplierDTO(supplierRepository.save(supplier1));
                            }).orElseThrow(() -> new ResourceNotFoundException("supplier with id:" + supplier.getSuppliersId() + " not exist"));
                    return supplierMapper.supplierToSupplierDTO(supplierRepository.save(supplier));
                }).orElseThrow(() -> new ResourceNotFoundException("citi with id:" + citiesId + " not exist")));
    }
}
