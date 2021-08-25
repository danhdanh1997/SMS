package com.xuandanh.sms.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipperDTO {
    private String shipperId;
    private String companyName;
    private long phone;
}
