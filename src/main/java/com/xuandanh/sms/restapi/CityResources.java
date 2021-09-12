package com.xuandanh.sms.restapi;

import com.xuandanh.sms.domain.City;
import com.xuandanh.sms.dto.CityDTO;
import com.xuandanh.sms.dto.CountryDTO;
import com.xuandanh.sms.exception.MyResourceNotFoundException;
import com.xuandanh.sms.mapper.CityMapper;
import com.xuandanh.sms.repository.CityRepository;
import com.xuandanh.sms.repository.CountryRepository;
import com.xuandanh.sms.service.CityService;
import com.xuandanh.sms.service.CountryService;
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
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CityResources {
    private final CityRepository cityRepository;
    private final CityService cityService;
    private final CityMapper cityMapper;
    private final CountryRepository countryRepository;
    private final CountryService countryService;
    private final Logger log = LoggerFactory.getLogger(CityResources.class);

    @GetMapping("/country/{countriesId}/city/{citiesId}")
    public ResponseEntity<?>getOne(@PathVariable(value = "countriesId")int countriesId,@PathVariable(value = "citiesId")int citiesId){
        if (!countryRepository.existsById(countriesId)){
            throw new MyResourceNotFoundException("country with id:"+countriesId+" not exist");
        }
        return ResponseEntity.ok(cityService.findOne(citiesId));
    }

    @GetMapping("/city/findAll")
    public ResponseEntity<?>getAll(){
        Map<String,Boolean> response = new HashMap<>();
        Optional<List<CityDTO>>cityDTOList = cityService.findAll();
        int sizeCity = 0;
        sizeCity = cityDTOList.map(List::size).orElse(0);
        if (sizeCity == 0){
            log.error("List city not exist");
            response.put("List city not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("show list city :"+cityDTOList);
        return ResponseEntity.ok(cityDTOList);
    }

    @PostMapping("/country/{countriesId}/city")
    public ResponseEntity<?>create(@PathVariable(value = "countriesId")int countriesId , @Valid @RequestBody City city){
        return ResponseEntity.ok(cityService.createCity(countriesId,city));
    }

    @PutMapping("/country/{countriesId}/city/{citiesId}")
    public ResponseEntity<?>update(@PathVariable(value = "countriesId")int countriesId,
                                   @PathVariable(value = "citiesId") int citiesId,
                                   @Valid @RequestBody City city){
        return ResponseEntity.ok(cityService.update(countriesId,city));
    }

    @DeleteMapping("/country/{countriesId}/city/{citiesId}")
    public ResponseEntity<?>delete(@PathVariable(value = "countriesId")int countriesId,
                                   @PathVariable(value = "citiesId")int citiesId){
        Map<String,Boolean>response = new HashMap<>();
        Optional<CountryDTO>countryDTO = countryService.findOne(countriesId);
        if ( countryDTO.stream().findFirst().isEmpty()){
            log.error("country with id:"+countriesId+" not exist");
            response.put("country with id:"+countriesId+" not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        return cityRepository.findById(citiesId)
                .map(city -> {
                    cityRepository.delete(city);
                    log.info("deleted city successfully");
                    response.put("deleted city successfully",Boolean.TRUE);
                    return ResponseEntity.ok(response);
                }).orElseThrow(()->new MyResourceNotFoundException("city with id:"+citiesId+" not exist"));
    }

    @GetMapping("/country/{countriesId}/city/findCityByCitiesName")
    public ResponseEntity<?>getCityByCitiesName(@PathVariable(value = "countriesId")int countriesId,
                                                @RequestParam(value = "page")int page,
                                                @RequestParam(value = "size")int size,
                                                @RequestParam(required = false)String citiesName){
        try {
                Map<String,Boolean>res = new HashMap<>();
                Optional<CountryDTO>countryDTO = countryService.findOne(countriesId);
                if (countryDTO.stream().findFirst().isEmpty()){
                    log.error("country with id:"+countriesId+" not exist");
                    res.put("country with id:"+countriesId+" not exist",Boolean.FALSE);
                    return ResponseEntity.ok(res);
                }
                List<CityDTO>cityDTOList = new ArrayList<CityDTO>();
                Pageable pageable = PageRequest.of(page,size);
                Page<City>cityDTOPage;
                if (citiesName == null)
                    cityDTOPage = cityRepository.findAll(pageable);
                else
                    cityDTOPage = cityRepository.findCitiesByCitiesName(pageable,citiesName);
                cityDTOList = cityMapper.cityToCityDTO(cityDTOPage.getContent());
                Map<String, Object> response = new HashMap<>();
                response.put("cityDTOList", cityDTOList);
                response.put("currentPage", cityDTOPage.getNumber());
                response.put("totalItems", cityDTOPage.getTotalElements());
                response.put("totalPages", cityDTOPage.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
