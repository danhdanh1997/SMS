package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.City;
import com.xuandanh.sms.dto.CityDTO;
import com.xuandanh.sms.dto.CountryDTO;
import com.xuandanh.sms.exception.MyResourceNotFoundException;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.CityMapper;
import com.xuandanh.sms.repository.CityRepository;
import com.xuandanh.sms.repository.CountryRepository;
import com.xuandanh.sms.restapi.SupplierResources;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CountryRepository countryRepository;
    private final CountryService countryService;
    private final Logger log = LoggerFactory.getLogger(CityService.class);

    public Optional<CityDTO>findOne(int citiesId){
        Optional<City>city = cityRepository.findById(citiesId);
        if (city.isEmpty()){
            log.error("city with id:"+citiesId+" not exist");
            return Optional.empty();
        }
        return Optional.ofNullable(cityMapper.cityToCityDTO(city.get()));
    }

    public Optional<List<CityDTO>>findAll(){
        return Optional.ofNullable(cityMapper.cityToCityDTO(cityRepository.findAll()));
    }

    public Optional<CityDTO>createCity(int countriesId , City city){
       Optional<CountryDTO>countryDTO = countryService.findOne(countriesId);
       if (countryDTO.isEmpty()){
           log.error("country with id:"+countriesId+" not exist");
           return Optional.empty();
       }
       return Optional.ofNullable(cityMapper.cityToCityDTO(cityRepository.save(city)));
    }


    public Optional<CityDTO>update(int countriesId, City city){
        return Optional.ofNullable(Optional.of(countryRepository
                .findById(countriesId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(country -> {
                    Optional.of(cityRepository
                            .findById(city.getCitiesId()))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(city1 -> {
                                city1.setCountriesId(country.getCountriesId());
                                city1.setCitiesName(city.getCitiesName());
                                city1.setLastUpdate(city.getLastUpdate());
                                return cityMapper.cityToCityDTO(cityRepository.save(city1));
                            }).orElseThrow(() -> new ResourceNotFoundException("city with id:" + city.getCitiesId() + " not exist"));
                    return cityMapper.cityToCityDTO(cityRepository.save(city));
                }).orElseThrow(() -> new ResourceNotFoundException("country with id:" + countriesId + " not exist")));
    }
}
