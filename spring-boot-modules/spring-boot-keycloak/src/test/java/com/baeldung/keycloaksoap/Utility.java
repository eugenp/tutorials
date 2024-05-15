package com.baeldung.keycloaksoap;

public class Utility {
    public static String getGetProductDetailsRequest() {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:key=\"http://www.baeldung.com/springbootsoap/keycloak\">\n" + "   <soapenv:Header/>\n" + "   <soapenv:Body>\n" + "      <key:getProductDetailsRequest>\n"
          + "         <key:id>1</key:id>\n" + "      </key:getProductDetailsRequest>\n" + "   </soapenv:Body>\n" + "</soapenv:Envelope>";
    }
    public static String getDeleteProductsRequest() {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:key=\"http://www.baeldung.com/springbootsoap/keycloak\">\n" + "   <soapenv:Header/>\n" + "   <soapenv:Body>\n" + "      <key:deleteProductRequest>\n"
          + "         <key:id>1</key:id>\n" + "      </key:deleteProductRequest>\n" + "   </soapenv:Body>\n" + "</soapenv:Envelope>";
    }
}
