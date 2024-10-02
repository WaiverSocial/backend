package social.nickrest.http.advanced;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import lombok.NonNull;
import social.nickrest.http.data.Type;
import social.nickrest.http.data.annotation.advanced.BaseEndPoint;
import social.nickrest.http.data.annotation.advanced.Path;
import social.nickrest.http.data.inter.IRequest;
import social.nickrest.http.data.inter.IResponse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AdvancedRequest implements IRequest {

    private final List<IAdvancedCallBack> callbacks = new ArrayList<>();
    private final String path;

    public AdvancedRequest() {
        BaseEndPoint endPoint = this.getClass().getAnnotation(BaseEndPoint.class);

        if(endPoint == null) {
            throw new IllegalArgumentException("EndPoint annotation is missing");
        }

        this.path = endPoint.value();

        for(Method method : this.getClass().getDeclaredMethods()) {
            if(!method.isAnnotationPresent(Path.class))
                continue;

            Path path = method.getAnnotation(Path.class);

            AdvancedCallback callback = getCallback(path.value(), path.type());

            if(callback != null)
                throw new IllegalArgumentException("Duplicate path " + path.value() + " " + path.type());

            callbacks.add(new AdvancedCallback(path, method, this));
        }
    }

    @Override
    public void handle(@NonNull IResponse response) {
        for(IAdvancedCallBack callback : callbacks) {
            String fixed = fixedPath(callback.getPath());

            if(response.getPath().equalsIgnoreCase(fixed) && response.getHttpType() == callback.getType()) {
                callback.handle(response);
                return;
            }
        }
    }


    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean advanced() {
        return true;
    }

    private AdvancedCallback getCallback(String path, Type type) {
        return (AdvancedCallback) callbacks.stream().filter(callback -> callback.getPath().equals(path) && callback.getType() == type).findFirst().orElse(null);
    }

    private String fixedPath(String path) {
        String base = this.path;
        if(base.endsWith("/") && path.startsWith("/")) {
            return base + path.substring(1);
        } else if(!base.endsWith("/") && !path.startsWith("/")) {
            return base + "/" + path;
        } else {
            return base + path;
        }
    }
}
