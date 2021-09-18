package com.xuandanh.sms.dto.cart;

import com.xuandanh.sms.domain.Cart;
import com.xuandanh.sms.domain.Product;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@RequiredArgsConstructor
@Getter
@Setter
@Data
public class CartItemDTO {
    public CartItemDTO(Cart cart) {
        this.setId(cart.getId());
        this.setCustomerId(cart.getCustomer().getCustomerId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
    private Long id;
    private @NotNull String customerId;
    private @NotNull Integer quantity;
    private @NotNull Product product;
}
