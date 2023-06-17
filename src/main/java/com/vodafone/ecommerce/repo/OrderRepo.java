package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query(value = "SELECT * FROM test.orders where customer_id=:customer_id and confirmed = true order by order_date desc",nativeQuery = true)
    List<Order> findAllOrdersByCustomerId(@Param("customer_id") Long id);

    @Query(value = "select r.product_id as itemId ,i.name as name ,i.image as image,i.price as price ,r.quantity as quantity\n" +
            "from test.relations as r , test.product as i\n" +
            "where r.order_id=:id and i.id=r.product_id ",nativeQuery = true)
    List<Projection> getProjection(@Param("id") Long id);
    @Query(value = "select * from test.orders where confirmed=false and customer_id=:customerId",nativeQuery = true)
    Order getUnconfirmedOrderByCustomerId(@Param("customerId") Long customerId);

    @Query(value = "update test.orders set confirmed = true , address=:address where id=:id",nativeQuery = true)
    @Transactional
    @Modifying
    void updateConfirmedById(@Param("id") Long id,@Param("address")String address);
}
