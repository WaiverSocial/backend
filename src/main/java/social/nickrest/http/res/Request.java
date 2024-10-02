package social.nickrest.http.res;

import lombok.NonNull;
import social.nickrest.http.data.annotation.EndPoint;
import social.nickrest.http.data.inter.IRequest;
import social.nickrest.http.data.Type;

public abstract class Request implements IRequest {

    @NonNull
    protected final String path;

    @NonNull
    protected final Type type;

    public Request() {
        EndPoint endPoint = this.getClass().getAnnotation(EndPoint.class);

        if(endPoint == null) {
            throw new IllegalArgumentException("EndPoint annotation is missing");
        }

        if(endPoint.path().isEmpty()) {
            throw new IllegalArgumentException("Path is empty");
        }

        this.path = endPoint.path();
        this.type = endPoint.type();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Type getType() {
        return type;
    }
}
