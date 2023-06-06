package com.vodafone.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "Cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "numOfItems")
    private Integer numOfItems;

    @Column(name = "totalPrice")
    private Integer totalPrice;

//    @Column(name = "productList")
//    private List<Product> productList;
}
