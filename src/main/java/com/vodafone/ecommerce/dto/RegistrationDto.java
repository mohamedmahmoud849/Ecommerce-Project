package com.vodafone.ecommerce.dto;

import lombok.Data;
import lombok.NonNull;


@Data
public class RegistrationDto {
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;

    public RegistrationDto() {

    }
}