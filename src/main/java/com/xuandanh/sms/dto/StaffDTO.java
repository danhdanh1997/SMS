package com.xuandanh.sms.dto;
import lombok.*;

import java.time.Instant;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private String staffId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private boolean active;
    private Instant lastUpdate;
    private String imageUrl;
    private int storeId;
}
