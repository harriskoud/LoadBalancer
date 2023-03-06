import com.DummyTomcat;
import com.Response;
import com.RoundRobinLoadBalancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LoadBalancerTest {

    private RoundRobinLoadBalancer loadBalancer;

    @BeforeEach
    public void setUp() {
        loadBalancer = new RoundRobinLoadBalancer();
    }

    @Test
    public void registerServer() {
        loadBalancer.registerServer(new DummyTomcat("dummy1"));

        assertTrue(loadBalancer.getServers().size() == 1);
    }

    @Test
    public void unregisterServer_serverExists() {
        DummyTomcat server = new DummyTomcat("dummy1");
        loadBalancer.registerServer(server);
        loadBalancer.unregisterServer(server);

        assertTrue(loadBalancer.getServers().size() == 0);
    }

    @Test
    public void unregisterServer_serverNotExists() {
        DummyTomcat server = new DummyTomcat("dummy1");
        loadBalancer.unregisterServer(server);

        assertTrue(loadBalancer.getServers().size() == 0);
    }

    @Test
    public void loadBalance_twoServers() {
        DummyTomcat server = new DummyTomcat("dummy1");
        DummyTomcat server2 = new DummyTomcat("dummy2");
        loadBalancer.registerServer(server);
        loadBalancer.registerServer(server2);

        Set<Response> collect = Stream.of("uri1", "uri2")
                .map(loadBalancer::loadBalance)
                .collect(Collectors.toSet());

        Iterator<Response> iterator = collect.iterator();
        var name = iterator.next().getServer().getName();
        var name2 = iterator.next().getServer().getName();

        assertTrue(name != name2);

        loadBalancer.unregisterServer(server);
        loadBalancer.unregisterServer(server2);
    }

    @Test
    public void loadBalance_twoServers_miltipleRequests() {
        DummyTomcat server = new DummyTomcat("dummy1");
        DummyTomcat server2 = new DummyTomcat("dummy2");
        loadBalancer.registerServer(server);
        loadBalancer.registerServer(server2);

        Stream.of("uri1", "uri2", "uri3", "uri4", "uri5")
                .map(loadBalancer::loadBalance)
                .collect(Collectors.toSet());

        assertTrue(loadBalancer.getRequests().size() > 0);

        loadBalancer.unregisterServer(server);
        loadBalancer.unregisterServer(server2);

    }

}
