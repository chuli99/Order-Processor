package ar.progamacion.julian.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.config.JHipsterProperties.Http;

@Service
@Transactional
public class CatedraService {

    private final Logger log = LoggerFactory.getLogger(CatedraService.class);

    public HttpResponse<String> getOrders() {
        String uri = "http://192.168.194.254:8000/api/ordenes/ordenes/";
        String token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWxpYW5jYXN0aWxsbzk5IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTczMDM2NTY0MH0.5UXTxeEBdh5Z_o63GqLrysSOfoUu-LmT96vWCvmzjXtAk3QhFuTUAkWyoxtsN35g1T9dUuxHbn9gk61Kz568tw";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("Authorization", "Bearer " + token).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("GET " + uri + " : " + response.statusCode());

            return response;
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }
}
