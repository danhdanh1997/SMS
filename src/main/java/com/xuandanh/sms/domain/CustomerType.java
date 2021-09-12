package com.xuandanh.sms.domain;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_type")
public class CustomerType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="customer_type_id")
    private int customerTypeId;

    @Column(name = "customer_type_name")
    private String customerTypeName;

//    @OneToMany(mappedBy="customerType",cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval = true)
//    private List<Customer>customers;
}
