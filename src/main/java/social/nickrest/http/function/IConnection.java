package social.nickrest.http.function;

import java.net.InetAddress;

public interface IConnection {
    void handle(InetAddress connectedAddress);
}
