package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Supplier;
import com.xuandanh.sms.dto.CityDTO;
import com.xuandanh.sms.dto.SupplierDTO;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.SupplierMapper;
import com.xuandanh.sms.repository.CityRepository;
import com.xuandanh.sms.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final CityRepository cityRepository;
    private final CityService cityService;
    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    public Optional<SupplierDTO>findOne(int supplierId){
        Optional<Supplier>supplier = supplierRepository.findById(supplierId);
        if (supplier.isEmpty()){
            log.error("supplier with id:"+supplierId+" not exist");
            return Optional.empty();
        }
        return Optional.ofNullable(supplierMapper.supplierToSupplierDTO(supplier.get()));
    }

    public Optional<List<SupplierDTO>>findAll(){
        return Optional.of(supplierMapper.supplierToSupplierDTO(supplierRepository.findAll()));
    }

    public Optional<SupplierDTO>createSupplier(int citiesId, Supplier supplier){
        Optional<CityDTO>cityDTO = cityService.findOne(citiesId);
        if (cityDTO.isEmpty()){
            log.error("city with id:"+citiesId+" not exist");
            return Optional.empty();
        }
        return Optional.ofNullable(supplierMapper.supplierToSupplierDTO(supplierRepository.save(supplier)));
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
                                return supplierMapper.supplierToSupplierDTO(supplierRepository.save(supplier1));
                            }).orElseThrow(() -> new ResourceNotFoundException("supplier with id:" + supplier.getSuppliersId() + " not exist"));
                    return supplierMapper.supplierToSupplierDTO(supplierRepository.save(supplier));
                }).orElseThrow(() -> new ResourceNotFoundException("citi with id:" + citiesId + " not exist")));
    }
}
