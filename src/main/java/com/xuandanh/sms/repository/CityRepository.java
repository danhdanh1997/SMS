package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.City;
import com.xuandanh.sms.dto.CityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    Page<City>findCitiesByCitiesName(Pageable pageable, String citiesName);
}
