package com.vodafone.ecommerce.serviceImpl;

import com.vodafone.ecommerce.Security.SecurityUtil;
import com.vodafone.ecommerce.errorhandlling.ProductOutOfStockException;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.ItemsQuantityProjection;
import com.vodafone.ecommerce.repo.OrderRepo;
import com.vodafone.ecommerce.repo.ProductRepo;
import com.vodafone.ecommerce.repo.Projection;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class ConfirmedOrderService implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final RelationService relationService;
    private final ProductRepo productRepo;
    private final UserService userService;


    @Override
    public Order addNewOrder(List<Product> list) {
        long totalQuantity=0L,totalPrice=0L;
        for (Product product:
                list) {
            totalQuantity += product.getQuantity();
            totalPrice += Long.valueOf(product.getPrice()) * product.getQuantity();
        }

        UserEntity loggedInCustomer = userService.findByEmail(SecurityUtil.getSessionUser());
        return orderRepo.save(Order.builder()
                .orderDate(new Date())
                .customer(loggedInCustomer)
                .itemsQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .confirmed(true)
                .build());
    }

    public List<Product> getCartItemsForOrderDetails(Long orderId) {
        List<Projection> projectionList = getProjection(orderId);
        List<Product> newCardItemsList = new ArrayList<>();
        List<Product> products = getProductsForOrderDetails(projectionList);
        products.stream().map(Product::getImage).toList();
        for(int i=0;i< projectionList.size();i++){
            newCardItemsList.add(Product.builder()
                    .name(products.get(i).getName())
                    .price(products.get(i).getPrice())
                    .image(products.get(i).getImage())
                    .quantity(Math.toIntExact(projectionList.get(i).getQuantity()))
                    .build());
        }
        return newCardItemsList;
    }
    public List<Order> getAllOrdersByCustomerId(Long id){
        return orderRepo.findAllOrdersByCustomerId(id);
    }

    public void setOrderProductsRelation(List<Product> productsList) {
        Order newOrder = addNewOrder(productsList);
        relationService.createRelations(productsList,newOrder);
    }

    public Order getOrderDetails(Long id) {
        return orderRepo.findById(id).get();
    }

    public List<Projection> getProjection(Long id) {
        return orderRepo.getProjection(id);
    }

    public List<Product> getProductsForOrderDetails(List<Projection> list) {
        return list.stream().flatMapToLong(x-> LongStream.of(x.getItemId())).mapToObj(productService::getProductById).toList();
    }


    //TODO: to be handled -> break this function into two functions
    public void handleStock(List<Product> products) {
        List<ItemsQuantityProjection> itemsQuantity = productRepo.getAllProductsQuantity();
        for (Product product:
             products) {
            ItemsQuantityProjection itemQuantity = itemsQuantity.stream().filter(x-> x.getProductId() == product.getId()).findFirst().get();
            int stockProductsQuantity = itemQuantity.getProductQuantity();
            int orderProductQuantity = product.getQuantity();
            if (orderProductQuantity > stockProductsQuantity){
                if (stockProductsQuantity == 0 ){
                    throw new ProductOutOfStockException("We are sorry but this  product : "+product.getName()+" currently out of stock.");
                }
                throw new ProductOutOfStockException("We are sorry but this  product : "+product.getName()+" quantity isn't available right now \n" +
                                                     "there's only : "+stockProductsQuantity+" available in stock from this product");
            }else{
                int updatedStockProductQuantity = stockProductsQuantity - orderProductQuantity;
                updateProductQuantity(updatedStockProductQuantity,product.getId());
            }
        }
    }

    public void updateProductQuantity(Integer quantity, Long id){
        productRepo.updateItemQuantityById(quantity,id);
    }


}
