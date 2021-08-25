package com.xuandanh.sms.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shippers")
public class Shippers {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(10)",name = "customer_id")
    @Id
    private String shipperId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "phone")
    private long phone;
}
