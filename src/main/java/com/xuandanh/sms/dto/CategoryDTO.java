package com.xuandanh.sms.dto;
import lombok.*;
import java.time.Instant;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private int categoriesId;
    private String categoriesName;
    private Instant lastUpdate;
}
