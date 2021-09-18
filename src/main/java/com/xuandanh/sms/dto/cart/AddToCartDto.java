package com.xuandanh.sms.dto.cart;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
@RequiredArgsConstructor
@Getter
@Setter
@Data
public class AddToCartDTO {
    private Long id;
    private @NotNull String productId;
    private @NotNull Integer quantity;
}
