package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.Security.SecurityUtil;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.OrderRepo;
import com.vodafone.ecommerce.repo.Projection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnConfirmedOrderService implements BaseOrderService {

    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final RelationService relationService;
    private final UserService userService;


    public Order getUnconfirmedOrderByCustomerId(Long id) {
        Order order = orderRepo.getUnconfirmedOrderByCustomerId(id);
        return order;
    }


    public List<Projection> getProjection(Long orderId) {
        return orderRepo.getProjection(orderId);
    }

    public List<Product> getProductsForOrderDetails(List<Projection> list) {
        return list.stream().flatMapToLong(x -> LongStream.of(x.getItemId())).mapToObj(productService::getProductById).toList();
    }

    public List<Product> getCardItemsForOrderDetails(Long orderId) {
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

    public List<Product> addNewCartItem(List<Product> cartItems, Long id , Integer quantity){
        Product newCartItem = productService.getProductById(id);
        if(cartItems.stream().anyMatch(x->x.getName().equals(newCartItem.getName()))){
            Product product= cartItems.stream().filter(x->x.getName().equals(newCartItem.getName())).findFirst().get();
            product.setQuantity(product.getQuantity()+quantity);
            cartItems.removeIf(x->x.getName().equals(product.getName()));
            cartItems.add(product);
            return cartItems;
        }else{
            log.info(String.valueOf(quantity));
            cartItems.add(Product.builder()
                    .id(id)
                    .name(newCartItem.getName())
                    .price(newCartItem.getPrice())
                    .image(newCartItem.getImage())
                    .quantity(quantity)
                    .build());
            return cartItems;
        }

    }

    public Order insertNewUnconfirmedOrder(List<Product> list){
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
                .confirmed(false)
                .build());
    }

    public void setOrderProductsRelation(List<Product> productsList){
        Order newOrder = insertNewUnconfirmedOrder(productsList);
        relationService.createRelations(productsList,newOrder);
    }

    public String calculateTotalPrice(List<Product> productsList) {
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

    public void handleStock(List<Product> productsList) {
        productService.handleStock(productsList);
    }

    public void confirmOrder(Long id) {
        orderRepo.updateConfirmedById(id);
    }

    public void deleteItemFromUnconfirmedOrderById(String name, Long id) {
        Product product = productService.getProductByName(name);
        relationService.deleteRelationBetweenItemAndOrder(product.getId(),id);
    }

    public void updateOrder(Long id) {
        List<Product> products = getCardItemsForOrderDetails(id);
        if (products.size()==0){
            orderRepo.deleteById(id);
        }
    }
}
