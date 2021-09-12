package com.xuandanh.sms.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cities_id")
    private int citiesId;

    @Column(name = "cities_name")
    private String citiesName;

    @Column(name = "last_update")
    private Instant lastUpdate = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "countriesId",nullable = false,referencedColumnName = "countries_id",insertable = false,updatable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="countriesId")
//    @JsonIdentityReference(alwaysAsId=true)
//    @JsonProperty("countriesId")
    private Country country;

    private int countriesId;

//    @OneToMany(mappedBy="city",cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval = true)
//    private List<Store>store;
//
//    @OneToMany(mappedBy="city",cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval = true)
//    private List<Supplier>suppliers;
}
