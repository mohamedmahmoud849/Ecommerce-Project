package com.vodafone.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vodafone.ecommerce.relation.relationEntity;
import jakarta.persistence.*;
import lombok.*;



import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "category")
    private String category;

    @Column(name = "rating")
    private Enum rating;

    @Lob
    //@JsonIgnore
    @Column(columnDefinition = "MEDIUMBLOB", length = 10485760)
    private String image;

    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<relationEntity> relations;

}
