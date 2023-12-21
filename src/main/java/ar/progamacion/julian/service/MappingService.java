package ar.progamacion.julian.service;

import ar.progamacion.julian.domain.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    public List<Order> processJson(String jsonBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Agrega el módulo JSR-310
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try {
            // Deserializar el JSON en un Map
            Map<String, Object> jsonMap = objectMapper.readValue(jsonBody, new TypeReference<Map<String, Object>>() {});

            // Obtener la lista de órdenes del mapa
            Object ordenesObj = jsonMap.get("ordenes");

            // Verificar que la lista de órdenes no sea nula y sea una instancia de ArrayList
            if (ordenesObj != null && ordenesObj instanceof ArrayList) {
                // Convertir la lista de órdenes a una lista de objetos Order
                return objectMapper.convertValue(ordenesObj, new TypeReference<List<Order>>() {});
            } else {
                System.out.println("La lista de órdenes no pudo ser obtenida o no es una instancia de ArrayList.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); // Retorna una lista vacía si hay algún problema
    }

    public static void main(String[] args) {
        CatedraService catedraService = new CatedraService();
        HttpResponse<String> response = catedraService.getOrders();
        //String body = getOrderService.getOrder();

        MappingService jsonProcessingService = new MappingService();
        List<Order> orders = jsonProcessingService.processJson(response.body());

        // Hacer algo con las órdenes (imprimir en este caso)
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
