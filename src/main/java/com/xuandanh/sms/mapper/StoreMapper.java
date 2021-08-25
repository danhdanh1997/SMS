package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Store;
import com.xuandanh.sms.dto.StoreDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class StoreMapper {
    public List<StoreDTO> storeToStoreDTO(List<Store> storeList) {
        return storeList.stream().filter(Objects::nonNull).map(this::storeToStoreDTO).collect(Collectors.toList());
    }

    public StoreDTO storeToStoreDTO(Store store){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(store,StoreDTO.class);
    }

    public List<Store> storeDTOToStore(List<StoreDTO> storeDTOList) {
        return storeDTOList.stream().filter(Objects::nonNull).map(this::storeDTOToStore).collect(Collectors.toList());
    }

    public Store storeDTOToStore(StoreDTO storeDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(storeDTO , Store.class);
    }
}