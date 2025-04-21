package edu.comillas.icai.gitt.pat.spring.p5;

import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class P5ApplicationE2ETest {

    private static final String NAME = "Name";
    private static final String EMAIL = "name@email.com";
    private static final String PASS = "aaaaaaA1";

    @Autowired
    TestRestTemplate client;

    @Test public void registerTest() {
        // Given ...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String registro = "{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.USER + "\"," +
                "\"password\":\"" + PASS + "\"}";

        // When ...
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/users",
                HttpMethod.POST, new HttpEntity<>(registro, headers), String.class);

        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.USER + "\"}",
                response.getBody());
    }

    // TODO#11
    /**
     * TODO#11
     * Completa el siguiente test E2E para que verifique la
     * respuesta de login cuando se proporcionan credenciales correctas
     */
    @Test public void loginOkTest() {
        // Given ... registrar al usuario primero
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Connection", "close");
        String registro = "{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.USER + "\"," +
                "\"password\":\"" + PASS + "\"}";

        client.exchange(
                "http://localhost:8080/api/users",
                HttpMethod.POST, new HttpEntity<>(registro, headers), String.class
        ).getBody();

        // Esperar un momento para que el servidor no esté en estado intermedio
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Ahora sí, hacer login
        // Ahora sí, hacer login usando otro cliente
        RestTemplate simpleClient = new RestTemplate();

        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);


        String loginRequest  = "{" +
                "\"email\":\"" + EMAIL + "\"," +
                "\"password\":\"" + PASS + "\"}";

        // When ...
        ResponseEntity<String> response = simpleClient.exchange(
                "http://localhost:8080/api/users/me/session",
                HttpMethod.POST, new HttpEntity<>(loginRequest, loginHeaders), String.class);

        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
