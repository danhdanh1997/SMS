package com.xuandanh.sms.dto;

import com.xuandanh.sms.domain.User;
import lombok.*;

import javax.validation.constraints.NotNull;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDTO {
    private Integer id;
    private @NotNull User user;
    private @NotNull Double totalPrice;
}
