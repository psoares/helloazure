package helloazure;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class HelloControllerTest {

    static EmbeddedServer embeddedServer;

    @BeforeAll
    public static void setup() {
        embeddedServer = ApplicationContext.run(
                EmbeddedServer.class
        );
    }

    @AfterAll
    public static void cleanup() {
        if (embeddedServer != null) {
            embeddedServer.stop();
        }
    }

    @Test
    void hello() {
        try (RxHttpClient client = embeddedServer.getApplicationContext().createBean(
                RxHttpClient.class, embeddedServer.getURL())) {
            assertEquals(HttpStatus.OK, client.toBlocking().exchange("/hello").status());
            assertEquals("Hello world!", client.toBlocking().exchange("/hello").getBody(String.class));
        }

    }
}
