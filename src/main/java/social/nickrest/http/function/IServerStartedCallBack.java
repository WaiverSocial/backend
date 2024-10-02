package social.nickrest.http.function;

import com.sun.net.httpserver.HttpServer;

public interface IServerStartedCallBack {
    void handle(HttpServer server);
}