package com.xuandanh.sms.dto;
import lombok.*;
import java.time.Instant;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private int suppliersId;
    private String supplierName;
    private long phone;
    private String websiteUrl;
    private int citiesId;
}
