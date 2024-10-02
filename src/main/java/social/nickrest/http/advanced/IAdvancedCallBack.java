package social.nickrest.http.advanced;

import lombok.NonNull;
import social.nickrest.http.data.Type;
import social.nickrest.http.data.inter.IResponse;

public interface IAdvancedCallBack {
    void handle(@NonNull IResponse response);
    Type getType();
    String getPath();
}
