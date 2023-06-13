package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    @Override
    @Query(value = "SELECT * FROM test.orders order by order_date desc",nativeQuery = true)
    List<Order> findAll();

    @Query(value = "select r.product_id as itemId ,i.name as name ,i.image as image,i.price as price ,r.quantity as quantity\n" +
            "from test.relations as r , test.product as i\n" +
            "where r.order_id=:id and i.id=r.product_id ",nativeQuery = true)
    List<Projection> getProjection(@Param("id") Long id);
}
