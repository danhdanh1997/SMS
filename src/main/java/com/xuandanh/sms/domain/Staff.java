package com.xuandanh.sms.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class Staff {
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "staff_id")
    @Id
    private String staffId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "last_update")
    private Instant lastUpdate = Instant.now();

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "storeId", nullable = false,referencedColumnName = "store_id",insertable = false,updatable = false)
    private Store store;

    private int storeId;

    public Staff(String staff_id,boolean active, String email, String first_name, String image_url, String last_name, String password, String username, int store_id) {
        this.staffId = staff_id;
        this.active = active;
        this.email = email;
        this.firstName = first_name;
        this.imageUrl = image_url;
        this.lastName = last_name;
        this.password = password;
        this.username = username;
        this.storeId = store_id;
    }

//    @OneToMany(mappedBy="staff",cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval = true)
//    private Set<Order> orders = new HashSet<>();
}
