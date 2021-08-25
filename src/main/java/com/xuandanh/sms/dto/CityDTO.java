package com.xuandanh.sms.dto;
import lombok.*;
import java.time.Instant;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private int citiesId;
    private String citiesName;
    private Instant lastUpdate ;
    private int countriesId;
}
