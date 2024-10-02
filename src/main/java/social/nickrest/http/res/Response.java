package social.nickrest.http.res;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;
import social.nickrest.http.data.Type;
import social.nickrest.http.data.inter.IResponse;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Response implements IResponse {

    private final Type httpType;
    private final String path;
    private final String body;
    private final HttpExchange exchange;
    private int status = 200;

    @Override
    public void write(byte[] response) {
        try {
            exchange.sendResponseHeaders(status, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBody() {
        return body.trim();
    }

    @Override
    public IResponse writeHeader(String key, String value) {
        exchange.getResponseHeaders().set(key, value);
        return this;
    }

    @Override
    public IResponse status(int status) {
        this.status = status;
        return this;
    }

    @Override
    public Map<String, String> getQuery() {
        return exchange.getRequestURI().getQuery() == null ? new HashMap<>() : parseQuery(exchange.getRequestURI().getQuery());
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            String allAfter = param.substring(param.indexOf("=") + 1);
            map.put(entry[0], entry.length > 1 ? allAfter : "");
        }
        return map;
    }

    @Override
    public Type getHttpType() {
        return httpType;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public HttpExchange getExchange() {
        return exchange;
    }

}
