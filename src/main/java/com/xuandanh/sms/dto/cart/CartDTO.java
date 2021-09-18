package com.xuandanh.sms.dto.cart;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;
@RequiredArgsConstructor
@Getter
@Setter
@Data
public class CartDTO {
    private List<CartItemDTO>cartItemDTOList;
    private double totalCost;

    public CartDTO(List<CartItemDTO> cartItems, double totalCost) {
        this.cartItemDTOList = cartItems;
        this.totalCost = totalCost;
    }
}
