package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Store;
import com.xuandanh.sms.dto.StoreDTO;
import com.xuandanh.sms.exception.MyResourceNotFoundException;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.StoreMapper;
import com.xuandanh.sms.repository.CityRepository;
import com.xuandanh.sms.repository.StoreRepository;
import com.xuandanh.sms.restapi.StoreResources;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.StoreManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final CityRepository cityRepository;
    private final Logger log = LoggerFactory.getLogger(StoreService.class);

    public Optional<StoreDTO>findOne(int storeId){
        return Optional.ofNullable(storeMapper
                .storeToStoreDTO(storeRepository
                .findById(storeId)
                .orElseThrow(()-> new ResourceNotFoundException("store with id:"+ storeId +" not exist"))));
    }

    public Optional<List<StoreDTO>>findAll(){
        return Optional.ofNullable(storeMapper
                .storeToStoreDTO(storeRepository.findAll()));
    }

    public Optional<StoreDTO>createStore(int citiesId , Store store){
        return Optional.ofNullable(cityRepository
                .findById(citiesId)
                .map(city -> {
                    store.setCitiesId(city.getCitiesId());
                    return storeMapper.storeToStoreDTO(storeRepository.save(store));
                }).orElseThrow(()-> new ResourceNotFoundException("city with id:"+citiesId +" not exist")));
    }

    public Optional<StoreDTO>updateStore(int citiesId, Store store) {
        return Optional.ofNullable(Optional.of(cityRepository
                .findById(citiesId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(city -> {
                    storeRepository.findById(store.getStoreId())
                            .map(store1 -> {
                                store1.setStoreName(store.getStoreName());
                                store1.setCitiesId(city.getCitiesId());
                                store1.setLastUpdate(store.getLastUpdate());
                                store1.setStaff(store.getStaff());
                                return storeMapper.storeToStoreDTO(storeRepository.save(store1));
                            }).orElseThrow(() -> new ResourceNotFoundException("store with id:" + store.getStoreId() + " not exist"));
                    return storeMapper.storeToStoreDTO(storeRepository.save(store));
                }).orElseThrow(() -> new ResourceNotFoundException("city with id:" + citiesId + " not exist")));
    }
}
