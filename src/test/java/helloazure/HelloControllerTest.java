package helloazure;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class HelloControllerTest {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void hello() {
        assertEquals(HttpStatus.OK, client.toBlocking().exchange("/hello").status());
        String response = client.toBlocking()
                .retrieve(HttpRequest.GET("/hello"));

        assertEquals("Hello azure!", response);
    }
}
