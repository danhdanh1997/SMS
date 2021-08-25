package com.xuandanh.sms.dto;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String productId;
    private String productName;
    private BigDecimal unitPrice;
    private int unitsInStock;
    private String imageUrl;
    private int unitsOnOrder;
    private int categoriesId;
    private int suppliersId;
}
