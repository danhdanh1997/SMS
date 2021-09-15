package com.xuandanh.sms.restapi;

import com.xuandanh.sms.domain.Country;
import com.xuandanh.sms.dto.CountryDTO;
import com.xuandanh.sms.exception.MyResourceNotFoundException;
import com.xuandanh.sms.mapper.CountryMapper;
import com.xuandanh.sms.repository.CountryRepository;
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
public class CountryResources {
    private final CountryService countryService;
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final Logger log = LoggerFactory.getLogger(CountryResources.class);

    @GetMapping("/country/findOne/{countriesId}")
    public ResponseEntity<?>getOne(@PathVariable(value = "countriesId")int countriesId){
        Map<String,Boolean>response = new HashMap<>();
        Optional<CountryDTO>countryDTO = countryService.findOne(countriesId);
        if (countryDTO.isEmpty()){
            response.put("country with id:"+countriesId+" not exist in database",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        log.info("info of country with id:"+countriesId+ ":"+countryDTO);
        return ResponseEntity.ok(countryDTO);
    }

    @GetMapping("/country/findAll")
    public ResponseEntity<?>getAll(){
        Map<String,Boolean>response = new HashMap<>();
        Optional<List<CountryDTO>>countryDTOList = countryService.findAll();
        int sizeCountry = 0;
        sizeCountry = countryDTOList.map(List::size).orElse(0);
        if (sizeCountry == 0){
            log.error("List country not exist");
            response.put("List country not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("show list country:"+countryDTOList);
        return ResponseEntity.ok(countryDTOList);
    }

    @PostMapping("/country/create")
    public ResponseEntity<?>create(@RequestBody Country country){
        Optional<CountryDTO>countryDTO = countryService.createCountry(country);
        Map<String,Boolean>response = new HashMap<>();
        if (countryDTO.isEmpty()){
            response.put("Object country invalid",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(countryDTO);
    }

    @PutMapping("/country/update/{countriesId}")
    public ResponseEntity<?> update(@PathVariable("countriesId")int countriesId,@Valid @RequestBody Country country){
        try {
            Optional<CountryDTO>countryDTO = countryService.findOne(countriesId);
            Map<String,Boolean>response = new HashMap<>();
                if(countryDTO.isPresent()){
                    response.put("update information of country with id:"+countriesId+" successfully",Boolean.TRUE);
                    return ResponseEntity.ok(countryService.updateCountry(country));
                }
                response.put("country with id:"+countriesId+" not exist in database",Boolean.FALSE);
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }catch (MyResourceNotFoundException e){
            log.error("country with id:"+countriesId+" not exist");
            throw new MyResourceNotFoundException("country with id:"+countriesId+"  not exist");
        }
    }

    @DeleteMapping("/country/delete/{countriesId}")
    public ResponseEntity<?> delete(@PathVariable("countriesId") int countriesId){
        Map<String,Boolean>response = new HashMap<>();
        return countryRepository.findById(countriesId)
                .map(country -> {
                    countryRepository.delete(country);
                    response.put("deleted country successfully",Boolean.TRUE);
                    return ResponseEntity.ok(response);
                }).orElseThrow(()->new MyResourceNotFoundException("country with id:"+countriesId+" not exist"));
    }

    @GetMapping("/country/findByCountryName")
    public ResponseEntity<?>getCountryByCountryName(@RequestParam(value = "page")int page,
                                                    @RequestParam(value = "size")int size,
                                                    @RequestParam(required = false)String countriesName){
        try {
                List<CountryDTO>countryList = new ArrayList<CountryDTO>();
                Pageable pageable = PageRequest.of(page,size);
                Page<Country>pageCountry;
                if (countriesName == null)
                    pageCountry = countryRepository.findAll(pageable);
                else
                    pageCountry = countryRepository.findByCountriesName(pageable,countriesName);
                countryList = countryMapper.countryToCountryDTO(pageCountry.getContent());
                Map<String, Object> response = new HashMap<>();
                response.put("countryList", countryList);
                response.put("currentPage", pageCountry.getNumber());
                response.put("totalItems", pageCountry.getTotalElements());
                response.put("totalPages", pageCountry.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
