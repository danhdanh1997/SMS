package com.xuandanh.sms.dto;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private int storeId;
    private String storeName;
    private Instant lastUpdate;
    private int citiesId;
    private List<StaffDTO>staffDTOList;
}
