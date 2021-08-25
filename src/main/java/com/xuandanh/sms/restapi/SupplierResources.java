package com.xuandanh.sms.restapi;

import com.xuandanh.sms.domain.Supplier;
import com.xuandanh.sms.dto.CityDTO;
import com.xuandanh.sms.dto.SupplierDTO;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.SupplierMapper;
import com.xuandanh.sms.repository.SupplierRepository;
import com.xuandanh.sms.service.CityService;
import com.xuandanh.sms.service.SupplierService;
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
public class SupplierResources {
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;
    private final SupplierRepository supplierRepository;
    private final CityService cityService;
    private final Logger log = LoggerFactory.getLogger(SupplierResources.class);

    @GetMapping("/city/{citiesId}/supplier/{supplierId}")
    public ResponseEntity<?>getOne(@PathVariable(value = "citiesId")int citiesId,
                                   @PathVariable(value = "supplierId")int supplierId){
        Map<String,Boolean>response = new HashMap<>();
        Optional<CityDTO>cityDTO = cityService.findOne(citiesId);
        if (cityDTO.get() == null){
            log.error("city with id:"+citiesId+" not exist");
            response.put("city with id:"+citiesId+" not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("Info of supplier with id:"+citiesId+" : "+cityDTO);
        return ResponseEntity.ok(supplierService.findOne(supplierId));
    }

    @GetMapping("/supplier/findAll")
    public ResponseEntity<?>getAll(){
        Optional<List<SupplierDTO>>supplierDTOList = supplierService.findAll();
        Map<String,Boolean>response = new HashMap<>();
        int sizeSupplier = 0;
        sizeSupplier = supplierDTOList.map(List::size).orElse(0);
        if (sizeSupplier == 0){
            log.error("List supplier is empty");
            response.put("List supplier is empty",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("Supplier List:"+supplierDTOList);
        return ResponseEntity.ok(supplierDTOList);
    }

    @PostMapping("/city/{citiesId}/supplier")
    public ResponseEntity<?>create(@PathVariable(value = "citiesId")int citiesId,
                                   @Valid @RequestBody Supplier supplier){
        return ResponseEntity.ok(supplierService.createSupplier(citiesId,supplier));
    }

    @PutMapping("/city/{citiesId}/supplier/{supplierId}")
    public ResponseEntity<?>update(@PathVariable(value = "citiesId")int citiesId,
                                   @PathVariable(value = "supplierId")int supplierId,
                                   @Valid @RequestBody Supplier supplier){
        Map<String,Boolean>response = new HashMap<>();
        Optional<SupplierDTO>supplierDTO = supplierService.findOne(supplierId);
        if (supplierDTO.get() == null){
            log.error("supplier with id:"+supplierId+" not exist");
            response.put("supplier with id:"+supplierId+" not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("Updated info of supplier with id:"+supplierId+" was successfully");
        return ResponseEntity.ok(supplierService.updateSupplier(citiesId,supplier));
    }

    @DeleteMapping("/city/{citiesId}/supplier/{supplierId}")
    public ResponseEntity<?>delete(@PathVariable(value = "citiesId")int citiesId,
                                   @PathVariable(value = "supplierId")int supplierId){
        Map<String,Boolean>response = new HashMap<>();
        Optional<CityDTO>cityDTO = cityService.findOne(citiesId);
        if (cityDTO.get() == null ){
            log.error("city with id:"+citiesId+" not exist");
            response.put("city with id:"+citiesId+" not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        return supplierRepository.findById(supplierId)
                .map(supplier -> {
                    log.info("Deleted supplier with id:"+supplierId+" was successfully");
                    supplierRepository.delete(supplier);
                    response.put("Deleted supplier with id:"+supplierId+" was successfully",Boolean.TRUE);
                    return ResponseEntity.ok(response);
                }).orElseThrow(()->new ResourceNotFoundException("supplier with id:"+supplierId+" not exist"));
    }

    @GetMapping("/supplier/findSupplierBySupplierName")
    public ResponseEntity<?>getSupplierBySupplierName(@RequestParam(value = "page")int page,
                                                      @RequestParam(value = "size")int size,
                                                      @RequestParam(required = false)String supplierName){
        try {
                List<SupplierDTO>supplierDTOList = new ArrayList<SupplierDTO>();
                Pageable pageable = PageRequest.of(page,size);
                Page<Supplier>supplierPage;
                if (supplierName == null)
                    supplierPage = supplierRepository.findAll(pageable);
                else
                    supplierPage = supplierRepository.findSupplierBySupplierName(pageable,supplierName);
                supplierDTOList = supplierMapper.supplierToSupplierDTO(supplierPage.getContent());
                Map<String, Object> response = new HashMap<>();
                response.put("supplierDTOList", supplierDTOList);
                response.put("currentPage", supplierPage.getNumber());
                response.put("totalItems", supplierPage.getTotalElements());
                response.put("totalPages", supplierPage.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
