package social.nickrest.http.data.inter;

import lombok.NonNull;
import social.nickrest.http.data.Type;

public interface IRequest {
    void handle(@NonNull IResponse response);
    String getPath();

    default Type getType() {
        return null;
    }
    default boolean advanced() {
        return false;
    };
}
