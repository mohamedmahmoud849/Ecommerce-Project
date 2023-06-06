package com.vodafone.ecommerce.model;

import com.vodafone.ecommerce.relation.relationEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "category")
    private String category;

    @Column(name = "rating")
    private Enum rating;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB", length = 10485760)
    private String image;

    @OneToMany(mappedBy = "product")
    private List<relationEntity> relations;

}
