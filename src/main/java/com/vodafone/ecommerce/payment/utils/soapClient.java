package com.vodafone.ecommerce.payment.utils;


import jakarta.xml.bind.JAXBElement;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import com.vodafone.ecommerce.payment.stubs.ValidateCardResponse;

public class soapClient extends WebServiceGatewaySupport {
    public ValidateCardResponse validateCard(String url , Object request){
        JAXBElement res = (JAXBElement) getWebServiceTemplate().marshalSendAndReceive(url,request);
        return (ValidateCardResponse) res.getValue();

    }

}
