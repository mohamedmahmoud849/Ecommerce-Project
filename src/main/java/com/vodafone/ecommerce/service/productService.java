package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class productService {

    private final ProductRepo productRepo;
    public void SaveImgDb(MultipartFile file, String name, Integer price, String category){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("not a prober file name");
        }else{

            try {
                productRepo.save(Product.builder()
                        .image(Base64.getEncoder().encodeToString(file.getBytes()))
                        .name(name)
                        .price(price)
                        .category(category)
                        .build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

