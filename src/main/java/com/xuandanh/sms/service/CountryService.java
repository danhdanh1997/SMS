package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Country;
import com.xuandanh.sms.dto.CountryDTO;
import com.xuandanh.sms.exception.MyResourceNotFoundException;
import com.xuandanh.sms.mapper.CountryMapper;
import com.xuandanh.sms.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;


    public Optional<CountryDTO> findOne(int countriesId){
        return Optional.ofNullable(countryMapper
                       .countryToCountryDTO(countryRepository
                       .findById(countriesId)
                       .orElseThrow(()-> new MyResourceNotFoundException("country with id:"+countriesId+" not exist"))));
    }


    public Optional<List<CountryDTO>> findAll(){
        return Optional.ofNullable(countryMapper.countryToCountryDTO(countryRepository.findAll()));
    }

    public Optional<CountryDTO> createCountry(Country country){
        return Optional.ofNullable(countryMapper.countryToCountryDTO(countryRepository.save(country)));
    }

    public CountryDTO updateCountry(Country country){
       Optional<Country>country1 = countryRepository.findById(country.getCountriesId());
       if (country1.isEmpty()){
           return null;
       }
       Country countryUpdate = country1.get();
       countryUpdate.setCountriesName(country.getCountriesName());
       countryUpdate.setLastUpdate(country.getLastUpdate());
       countryUpdate.setCities(country.getCities());
       return countryMapper.countryToCountryDTO(countryUpdate);
    }

    public void deleteCountry(Country country){
        countryRepository.delete(country);
    }
}
