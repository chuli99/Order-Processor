package um.ar.prog.service;

import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class GetOrderService {

    private final Logger log = LoggerFactory.getLogger(GetOrderService.class);

    public static void main(String[] args) throws URISyntaxException {
        String url = "http://192.168.194.254:8000/api/ordenes/ordenes/";
        String jwtToken =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWxpYW5jYXN0aWxsbzk5IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTczMDM2NTY0MH0.5UXTxeEBdh5Z_o63GqLrysSOfoUu-LmT96vWCvmzjXtAk3QhFuTUAkWyoxtsN35g1T9dUuxHbn9gk61Kz568tw";

        //Instancia RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Encabezado con JWT
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(url));

        //Obtiene la respuesta
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Solicitud exitosa
            String responseBody = response.getBody();
            System.out.println("Solicitud exitosa. Respuesta:");
            System.out.println(responseBody);
        } else {
            // Error en la solicitud
            System.out.println("Error en la solicitud. Código de estado: " + response.getStatusCodeValue());
            System.out.println(response.getBody()); // Puede contener información adicional sobre el error
        }
    }
}