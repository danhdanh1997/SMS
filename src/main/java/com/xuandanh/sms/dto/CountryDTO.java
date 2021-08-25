package com.xuandanh.sms.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {
    private int countriesId;
    private String countriesName;
    private Instant lastUpdate;
    private List<CityDTO>cityDTOList;
}
