package com.vodafone.ecommerce.config;

import com.vodafone.ecommerce.payment.utils.soapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class soapConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.vodafone.ecommerce.payment.stubs");
        return marshaller;
    }

    @Bean
    public soapClient countryClient(Jaxb2Marshaller marshaller) {
        soapClient client = new soapClient();
        client.setDefaultUri("http://localhost:9090/wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
