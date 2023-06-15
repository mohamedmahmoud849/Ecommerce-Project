package com.vodafone.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vodafone.ecommerce.relation.relationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date orderDate;
    private Long totalPrice;
    private boolean confirmed;
    @OneToMany(mappedBy = "order")
    /*@JsonIgnore*/
    private List<relationEntity> relations;
    private Long itemsQuantity;
    @ManyToOne
    private UserEntity customer;
}
