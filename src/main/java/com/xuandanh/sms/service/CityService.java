package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.City;
import com.xuandanh.sms.dto.CityDTO;
import com.xuandanh.sms.exception.MyResourceNotFoundException;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.CityMapper;
import com.xuandanh.sms.repository.CityRepository;
import com.xuandanh.sms.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CountryRepository countryRepository;
    public Optional<CityDTO>findOne(int citiesId){
        return Optional.ofNullable(cityMapper
                .cityToCityDTO(cityRepository.findById(citiesId)
                .orElseThrow(()->new ResourceNotFoundException("city with id:"+citiesId+" not exist"))));
    }

    public Optional<List<CityDTO>>findAll(){
        return Optional.ofNullable(cityMapper.cityToCityDTO(cityRepository.findAll()));
    }

    public Optional<CityDTO>createCity(int countriesId , City city){
        return Optional.ofNullable(countryRepository
                .findById(countriesId)
                .map(country -> {
                    city.setCountriesId(country.getCountriesId());
                    return cityMapper.cityToCityDTO(cityRepository.save(city));
                }).orElseThrow(()->new ResourceNotFoundException("country with id:"+countriesId+" not exist")));
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
                                city1.setStore(city.getStore());
                                city1.setSuppliers(city.getSuppliers());
                                return cityMapper.cityToCityDTO(cityRepository.save(city1));
                            }).orElseThrow(() -> new ResourceNotFoundException("city with id:" + city.getCitiesId() + " not exist"));
                    return cityMapper.cityToCityDTO(cityRepository.save(city));
                }).orElseThrow(() -> new ResourceNotFoundException("country with id:" + countriesId + " not exist")));
    }
}
