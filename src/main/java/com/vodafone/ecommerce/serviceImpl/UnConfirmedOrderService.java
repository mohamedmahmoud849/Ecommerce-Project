package com.vodafone.ecommerce.serviceImpl;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.OrderRepo;
import com.vodafone.ecommerce.repo.Projection;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnConfirmedOrderService implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final RelationService relationService;
    private final UserService userService;
    private final ConfirmedOrderService orderService;


    public Order addNewOrder(List<Product> list){
        long totalQuantity=0L,totalPrice=0L;
        for (Product product:
                list) {
            totalQuantity += product.getQuantity();
            totalPrice += Long.valueOf(product.getPrice()) * product.getQuantity();
        }
        UserEntity loggedInCustomer = userService.findById(getCurrentUserId());
        return orderRepo.save(Order.builder()
                .orderDate(new Date())
                .customer(loggedInCustomer)
                .itemsQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .confirmed(false)
                .build());
    }

    public List<Product> getCartItemsForOrderDetails(Long orderId) {
        List<Projection> projectionList = getProjection(orderId);
        List<Product> newCardItemsList = new ArrayList<>();
        List<Product> products = getProductsForOrderDetails(projectionList);
        products.stream().map(Product::getImage).toList();
        for (int i = 0; i < projectionList.size(); i++) {
            newCardItemsList.add(Product.builder()
                    .id(products.get(i).getId())
                    .name(products.get(i).getName())
                    .price(products.get(i).getPrice())
                    .image(products.get(i).getImage())
                    .quantity(Math.toIntExact(projectionList.get(i).getQuantity()))
                    .build());
        }
        return newCardItemsList;
    }
    public Order getCurentUserUnconfirmedOrder() {
        UserEntity loggedInCustomer = userService.getCurrentLoggedInUser();
        Order order = orderRepo.getUnconfirmedOrderByCustomerId(loggedInCustomer.getId());
        return order;
    }

    public List<Product> getCurentUserUnconfirmedOrderProductsList(){
        Order unconfirmedOrder = getCurentUserUnconfirmedOrder();
        if(unconfirmedOrder!=null){
            //return List of Products and Total Price
            return getCartItemsForOrderDetails(unconfirmedOrder.getId());
        }else{
            return new ArrayList<>();
        }
    }
    public Long getCurrentUserId(){
        UserEntity currentUser = userService.getCurrentLoggedInUser();
        return currentUser.getId();
    }
    public List<Projection> getProjection(Long orderId) {
        return orderRepo.getProjection(orderId);
    }

    public List<Product> getProductsForOrderDetails(List<Projection> list) {
        return list.stream().flatMapToLong(x -> LongStream.of(x.getItemId())).mapToObj(productService::getProductById).toList();
    }


    public void addNewCartItem(Long id , Integer quantity){
        Product orderedProduct = productService.getProductById(id);
        Order currentOrder = getCurentUserUnconfirmedOrder();
        List<Product> currentCartItems = getCurentUserUnconfirmedOrderProductsList();
        if (currentCartItems.isEmpty()){
            Product newProduct = Product.builder()
                            .id(orderedProduct.getId())
                                    .quantity(quantity)
                                            .category(orderedProduct.getCategory())
                                                    .image(orderedProduct.getImage())
                                                            .name(orderedProduct.getName())
                                                                    .price(orderedProduct.getPrice())
                                                                            .build();
            currentCartItems.add(newProduct);
            setOrderProductsRelation(currentCartItems);
        }else{
            if(currentCartItems.stream().anyMatch(x->x.getName().equals(orderedProduct.getName()))){
                Product product= currentCartItems.stream().filter(x->x.getName().equals(orderedProduct.getName())).findFirst().get();
                product.setQuantity(product.getQuantity()+quantity);
                currentCartItems.removeIf(x->x.getName().equals(product.getName()));
                currentCartItems.add(product);
                deleteUnconfirmedOrderTOUpdate(currentOrder.getId());
                setOrderProductsRelation(currentCartItems);
            }else{
                currentCartItems.add(Product.builder()
                        .id(id)
                        .name(orderedProduct.getName())
                        .price(orderedProduct.getPrice())
                        .image(orderedProduct.getImage())
                        .quantity(quantity)
                        .build());
                deleteUnconfirmedOrderTOUpdate(currentOrder.getId());
                setOrderProductsRelation(currentCartItems);
            }
        }

    }



    public void setOrderProductsRelation(List<Product> productsList){
        Order newOrder = addNewOrder(productsList);
        relationService.createRelations(productsList,newOrder);
    }

    public String calculateOrderTotalPrice(List<Product> productsList) {
        Long totalPrice = 0L;
        if (productsList.isEmpty()) {
            return String.valueOf(totalPrice);
        } else {
            for (Product product :
                    productsList) {
                totalPrice += Long.valueOf(product.getPrice()) * product.getQuantity();
            }
            return String.valueOf(totalPrice);
        }
    }

    public void deleteUnconfirmedOrderTOUpdate(Long id) {
        orderRepo.deleteById(id);
        relationService.deleteAllRelationsWithOrderById(id);
    }



    public void confirmOrder(Long id,String address) {

        orderRepo.updateConfirmedById(id,address);
    }

    public void deleteItemFromUnconfirmedOrderById(String name) {
        Order currentCart = getCurentUserUnconfirmedOrder();
        List<Product> currentCartItems = getCurentUserUnconfirmedOrderProductsList();
        currentCartItems.removeIf(x-> Objects.equals(x.getName(), name));
        deleteUnconfirmedOrderTOUpdate(currentCart.getId());
        if (!currentCartItems.isEmpty()){
            setOrderProductsRelation(currentCartItems);
        }
    }


    public void updateUnconfirmedOrderItemQuantity(Long itemId, Integer itemQuantity) {
        Long orderId = getCurentUserUnconfirmedOrder().getId();
        productService.isThereEnoughStock(itemId,itemQuantity);
        relationService.updateOrderItemRelation(orderId,itemId,itemQuantity);
    }
}
