package com.example.parameta.employee.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class SoapClient {

    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    public SoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public Object callSoapService(String uri, Object requestPayload) {
        System.out.println("Calling SOAP service with: " + requestPayload.getClass());
        return webServiceTemplate.marshalSendAndReceive(uri, requestPayload);
    }
}