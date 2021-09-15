package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Country;
import com.xuandanh.sms.dto.CountryDTO;
import com.xuandanh.sms.exception.MyResourceNotFoundException;
import com.xuandanh.sms.mapper.CountryMapper;
import com.xuandanh.sms.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final Logger log = LoggerFactory.getLogger(CountryService.class);

    public Optional<CountryDTO> findOne(int countriesId){
        Optional<Country>country = countryRepository.findById(countriesId);
        if (country.isEmpty()){
            log.error("country with id:"+countriesId+" not exist");
            return Optional.empty();
        }
        return Optional.ofNullable(countryMapper.countryToCountryDTO(country.get()));
    }


    public Optional<List<CountryDTO>> findAll(){
        return Optional.ofNullable(countryMapper.countryToCountryDTO(countryRepository.findAll()));
    }

    public Optional<CountryDTO> createCountry(Country country){
        return Optional.ofNullable(countryMapper.countryToCountryDTO(countryRepository.save(country)));
    }

    public Optional<CountryDTO> updateCountry(Country country){
       Optional<Country>country1 = countryRepository.findById(country.getCountriesId());
       if (country1.isEmpty()){
           log.error("country with id:"+country.getCountriesId()+" not exist");
           return Optional.empty();
       }
       Country countryUpdate = country1.get();
       countryUpdate.setCountriesName(country.getCountriesName());
       countryUpdate.setLastUpdate(country.getLastUpdate());
       return Optional.ofNullable(countryMapper.countryToCountryDTO(countryUpdate));
    }

    public void deleteCountry(Country country){
        countryRepository.delete(country);
    }
}
