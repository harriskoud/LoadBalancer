import com.DummyTomcat;
import com.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
public class ServerTest {

    private DummyTomcat server;

    @BeforeEach
    public void setUp() {
        server = new DummyTomcat("Dummy Server");
        server.start();
    }

    @AfterEach
    public void cleanUp() {
        server.stop();
    }

    @Test
    public void startServer() {
        assertTrue(server.isRunning());
    }

    @Test
    public void stopServer() {
        server.stop();
        Assertions.assertFalse(server.isRunning());
    }

    @Test
    public void handleRequest_serverIsRunning() {
        Response response = server.handleRequest("http://uri");

        assertTrue(response.getUri().equalsIgnoreCase("http://uri"));
    }

    @Test
    public void handleRequest_serverIsDown() {
        server.stop();
        assertThrows(RuntimeException.class, () -> server.handleRequest("http://uri"));
    }
}
