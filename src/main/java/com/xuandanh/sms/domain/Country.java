package com.xuandanh.sms.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="countries_id")
    private int countriesId;

    @Column(name = "countries_name")
    private String countriesName;

    @Column(name = "last_update")
    private Instant lastUpdate = Instant.now();

    @OneToMany(mappedBy="country",cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval = true)
    private List<City>cities;
}
