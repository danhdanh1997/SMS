package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
    @Query("select c from Country c where c.countriesName like %:countriesName%")
    Page<Country>findByCountriesName(Pageable pageable, String countriesName);
}
