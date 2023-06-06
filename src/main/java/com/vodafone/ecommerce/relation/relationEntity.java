package com.vodafone.ecommerce.relation;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "relations")
public class relationEntity {
    @EmbeddedId
    private compositeKey id;

    @ManyToOne
    @MapsId(value = "orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId(value = "productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private long quantity;

}