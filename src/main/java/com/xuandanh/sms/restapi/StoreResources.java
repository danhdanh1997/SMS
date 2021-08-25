package com.xuandanh.sms.restapi;

import com.xuandanh.sms.domain.Store;
import com.xuandanh.sms.dto.CityDTO;
import com.xuandanh.sms.dto.StoreDTO;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.CityMapper;
import com.xuandanh.sms.mapper.StoreMapper;
import com.xuandanh.sms.repository.CityRepository;
import com.xuandanh.sms.repository.StoreRepository;
import com.xuandanh.sms.service.CityService;
import com.xuandanh.sms.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "xuandanh")
public class StoreResources {
    private final StoreRepository storeRepository;
    private final StoreService storeService;
    private final CityRepository cityRepository;
    private final StoreMapper storeMapper;
    private final CityService cityService;
    private final Logger log = LoggerFactory.getLogger(StoreResources.class);

    @GetMapping("/city/{citiesId}/store/{storeId}")
    public ResponseEntity<?>getOne(@PathVariable(value = "storeId")int storeId,
                                   @PathVariable(value = "citiesId")int citiesId){
        if (!cityRepository.existsById(citiesId)){
            log.error("city with id:"+citiesId+" not exist");
            throw new ResourceNotFoundException("city with id:"+citiesId+" not exist");
        }
        log.info("information of store with id:"+ storeId+" : "+ storeService.findOne(storeId));
        return ResponseEntity.ok(storeService.findOne(storeId));
    }

    @GetMapping("/store/findAll")
    public ResponseEntity<?>getAll(){
        Map<String,Boolean>response = new HashMap<>();
        Optional<List<StoreDTO>>storeDTOList = storeService.findAll();
        int sizeStore = storeDTOList.map(List::size).orElse(0);
        if (sizeStore == 0){
            log.error("List store is empty");
            response.put("List store is empty",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("Show list store :"+ storeDTOList);
        return ResponseEntity.ok(storeDTOList);
    }

    @PostMapping("/city/{citiesId}/store")
    public ResponseEntity<?>create(@PathVariable(value = "citiesId")int citiesId,
                                   @Valid @RequestBody Store store){
        return ResponseEntity.ok(storeService.createStore(citiesId,store));
    }

    @PutMapping("/city/{citiesId}/store/{storeId}")
    public ResponseEntity<?>updateStore(@PathVariable(value = "citiesId")int citiesId,
                                        @PathVariable(value = "storeId")int storeId,
                                        @Valid @RequestBody Store store){
        Optional<CityDTO>cityDTO = cityService.findOne(citiesId);
        Optional<StoreDTO>storeDTO = storeService.findOne(storeId);
        Map<String,Boolean>response = new HashMap<>();
        if (cityDTO.isEmpty() && storeDTO.isEmpty()){
            log.error("city not found && store not found");
            response.put("city not found && store not found",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("update store successfully");
        return ResponseEntity.ok(storeService.updateStore(citiesId,store));
    }

    @DeleteMapping("/city/{citiesId}/store/{storeId}")
    public ResponseEntity<?>delete(@PathVariable(value = "citiesId")int citiesId,
                                   @PathVariable(value = "storeId")int storeId){
        Map<String,Boolean>response = new HashMap<>();
        Optional<CityDTO>cityDTO = cityService.findOne(citiesId);
        if (cityDTO.stream().findFirst().isEmpty()){
            log.error("city with id:"+citiesId +" not exist");
            response.put("city with id:"+citiesId +" not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        return storeRepository.findById(storeId)
                .map(store -> {
                    log.info("deleted store with id:"+storeId+" was successfully");
                    response.put("deleted store with id:"+storeId+" was successfully",Boolean.TRUE);
                    storeRepository.delete(store);
                    return ResponseEntity.ok(response);
                }).orElseThrow(()->new ResourceNotFoundException("store with id:"+storeId+" not exist"));
    }

    @GetMapping("/city/{citiesId}/store/findByStoreName")
    public ResponseEntity<?>getStoreByStoreName(@PathVariable(value = "citiesId")int citiesId,
                                                @RequestParam(value = "page")int page,
                                                @RequestParam(value = "size")int size,
                                                @RequestParam(required = false)String storeName){
        try {
                Map<String,Boolean>respon = new HashMap<>();
                Optional<CityDTO>cityDTO = cityService.findOne(citiesId);
                if (cityDTO.stream().findFirst().isEmpty()){
                    log.error("city with id:"+citiesId+" not exist");
                    respon.put("city with id:"+citiesId+" not exist",Boolean.FALSE);
                    return ResponseEntity.ok(respon);
                }
                List<StoreDTO>storeList = new ArrayList<StoreDTO>();
                Pageable pageable = PageRequest.of(page, size);
                Page<Store> pageStore;
                if (storeName == null)
                    pageStore = storeRepository.findAll(pageable);
                else
                    pageStore = storeRepository.findByStoreName(pageable,storeName);
                storeList = storeMapper.storeToStoreDTO(pageStore.getContent());
                Map<String, Object> response = new HashMap<>();
                response.put("storeList", storeList);
                response.put("currentPage", pageStore.getNumber());
                response.put("totalItems", pageStore.getTotalElements());
                response.put("totalPages", pageStore.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
