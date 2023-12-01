package ar.progamacion.julian.service;

import ar.progamacion.julian.domain.Order;
import ar.progamacion.julian.web.rest.OrderResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.http.ResponseEntity;

public class test {

    public static void main(String[] args) throws URISyntaxException {
        GetOrderService getOrderService = new GetOrderService();
        String body = getOrderService.getOrder();
        System.out.println(body);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Agrega el módulo JSR-310
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(dateFormat);

        try {
            // Deserializar el JSON en un Map
            Map<String, Object> jsonMap = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});

            // Obtener la lista de órdenes del mapa
            Object ordenesObj = jsonMap.get("ordenes");

            // Verificar que la lista de órdenes no sea nula y sea una instancia de ArrayList
            if (ordenesObj != null && ordenesObj instanceof ArrayList) {
                // Convertir la lista de órdenes a una lista de objetos Order
                List<Order> ordenes = objectMapper.convertValue(ordenesObj, new TypeReference<List<Order>>() {});

                // Ahora, 'ordenes' es una lista de objetos Order
                for (Order orden : ordenes) {
                    orderResource.createOrder(orden);
                    System.out.println(orden);
                }
            } else {
                System.out.println("La lista de órdenes no pudo ser obtenida o no es una instancia de ArrayList.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
