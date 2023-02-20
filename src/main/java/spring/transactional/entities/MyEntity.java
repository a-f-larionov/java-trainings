package spring.transactional.entities;

import jakarta.persistence.*;

@Entity
public class MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
}
