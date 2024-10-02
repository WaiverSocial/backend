package social.nickrest.http;

import com.sun.net.httpserver.HttpServer;
import social.nickrest.http.data.Type;
import social.nickrest.http.data.inter.IRequest;
import social.nickrest.http.function.IConnection;
import social.nickrest.http.function.IServerStartedCallBack;
import social.nickrest.http.res.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class HTTPBuilder {

    public final List<IRequest> requests = new ArrayList<>();
    private IConnection connection;

    public void listen(int port) {
        listen(port, null);
    }

    public void listen(int port, IServerStartedCallBack startedCallBack) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            if(duplicateCheck())
                throw new IllegalArgumentException("Duplicate path and type");

            server.createContext("/", (exchange) -> {
                if(connection != null) {
                    InetAddress address = exchange.getRemoteAddress().getAddress();
                    connection.handle(address);
                }

                InputStream is = exchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }

                String requestBody = body.toString();
                String requestMethod = exchange.getRequestMethod();
                String path = exchange.getRequestURI().getPath();

                for (IRequest request : requests) {
                    if(request.advanced() && path.startsWith(request.getPath())) {
                        request.handle(new Response(Type.fromString(requestMethod), path, requestBody, exchange));
                        return;
                    }

                    if (requestMethod.equalsIgnoreCase(request.getType().name()) && path.equals(request.getPath())) {
                        Response response = new Response(Type.fromString(requestMethod), path, requestBody, exchange);
                        request.handle(response);
                    }
                }

                Response response = new Response(Type.fromString(requestBody), path, requestBody, exchange);
                response.status(404)
                        .writeHeader("Content-Type", "text/html")
                        .write("<html><head><title>Error</title></head><body><pre>Cannot " + requestMethod + " " + path + "</pre></body></html>");
            });

            server.setExecutor(null);
            server.start();

            if(startedCallBack == null) return;

            startedCallBack.handle(server);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean duplicateCheck() {
        for (int i = 0; i < requests.size(); i++) {
            IRequest request = requests.get(i);
            for (int j = i + 1; j < requests.size(); j++) {
                IRequest request2 = requests.get(j);
                if (request.getPath().equals(request2.getPath()) && request.getType() == request2.getType()) {

                    if((request.advanced() && request2.advanced()) || !request.advanced() && !request2.advanced()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public HTTPBuilder endpoint(IRequest request) {
        requests.add(request);
        return this;
    }

    public HTTPBuilder connection(IConnection connection) {
        this.connection = connection;
        return this;
    }

    public static HTTPBuilder create() {
        return new HTTPBuilder();
    }

}
