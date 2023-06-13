package com.vodafone.ecommerce.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mail {
    private String recipient;
    private String subject;
    private String message;
}
