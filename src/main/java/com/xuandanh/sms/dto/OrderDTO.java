package com.xuandanh.sms.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String orderId;
    private Date orderDate;
    private Date requiredDate;
    private Date shippedDate;
    private BigDecimal freight;
    private String shipName;
    private String shipAddress;
    private String shipCode;
    private CityDTO cityDTO;
    private String productId;
    private String staffId;
}
