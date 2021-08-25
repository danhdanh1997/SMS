package com.xuandanh.sms.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private String productId;
    private String orderId;
    private BigDecimal unitPrice;
    private int quantity;
    private float discount;
}
