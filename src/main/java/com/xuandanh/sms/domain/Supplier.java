package com.xuandanh.sms.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="suppliers_id")
    private int suppliersId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "phone")
    private BigInteger phone;

    @Column(name = "website_url")
    private String websiteUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "citiesId", nullable = false,referencedColumnName = "cities_id",insertable = false,updatable = false)
    private City city;

    private int citiesId;

//    @OneToMany(mappedBy="supplier")
//    private List<Product> products;
}
