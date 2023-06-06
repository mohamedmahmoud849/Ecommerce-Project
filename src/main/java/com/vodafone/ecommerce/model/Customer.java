package com.vodafone.ecommerce.model;

import jakarta.persistence.*;
@Entity
public class Customer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
