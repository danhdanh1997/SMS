package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Country;
import com.xuandanh.sms.dto.CountryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class CountryMapper {
    public List<CountryDTO> countryToCountryDTO(List<Country> countryList) {
        return countryList.stream().filter(Objects::nonNull).map(this::countryToCountryDTO).collect(Collectors.toList());
    }

    public CountryDTO countryToCountryDTO(Country country){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(country,CountryDTO.class);
    }

    public List<Country> countryDTOToCountry(List<CountryDTO> countryDTOList) {
        return countryDTOList.stream().filter(Objects::nonNull).map(this::countryDTOToCountry).collect(Collectors.toList());
    }

    public Country countryDTOToCountry(CountryDTO countryDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(countryDTO , Country.class);
    }
}