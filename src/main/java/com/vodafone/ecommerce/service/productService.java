package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class productService {

    private final ProductRepo productRepo;


    public Product getProductById(Long id) {
        return productRepo.findById(id).get();
    }

    public List<Product> getALlByCategory(String category) {
        return productRepo.findAllByCategory(category);
    }

    public List<Product> getALl(){
        return productRepo.findAll();
    }

    public void SaveImgDb(MultipartFile file, String name, Integer price, String category, Integer quantity) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a proper file name");
        } else {

            try {
                productRepo.save(Product.builder()
                        .image(Base64.getEncoder().encodeToString(file.getBytes()))
                        .name(name)
                        .quantity(quantity)
                        .price(price)
                        .category(category)
                        .build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
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
}

