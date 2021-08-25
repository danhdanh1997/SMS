package com.xuandanh.sms.dto;
import lombok.*;

import java.time.Instant;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;
    private Instant createDate;
    private Instant lastUpdate;
    private int customerTypeId;
}
