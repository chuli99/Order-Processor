package ar.progamacion.julian.service;

import java.net.URISyntaxException;
import org.json.JSONObject;

public class test {

    public static void main(String[] args) throws URISyntaxException {
        GetOrderService getOrderService = new GetOrderService();
        String body = getOrderService.getOrder();
        System.out.println(body);
        System.out.println(body.length());
    }
}
