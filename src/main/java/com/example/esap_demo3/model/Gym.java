package com.example.esap_demo3.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="gyms")
@Getter
@Setter
@RequiredArgsConstructor
public class Gym implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "gym_id_seq", allocationSize = 1)
    @Column(name = "gym_id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "gym_num")
    private Integer gymNum;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SeasonPass> passes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Gym gym = (Gym) o;
        return id != null && Objects.equals(id, gym.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}