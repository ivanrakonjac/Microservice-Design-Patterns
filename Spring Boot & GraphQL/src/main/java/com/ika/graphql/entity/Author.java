package com.ika.graphql.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude // Set<Book> books will not be taken into consideration for toString() method
    @EqualsAndHashCode.Exclude // Set<Book> books will not be taken into consideration for EqualsAndHashCode() method
    @JsonManagedReference
    private Set<Book> books = new HashSet<>();

}
