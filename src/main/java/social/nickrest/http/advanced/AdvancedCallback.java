package social.nickrest.http.advanced;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import social.nickrest.http.data.Type;
import social.nickrest.http.data.annotation.advanced.Path;
import social.nickrest.http.data.inter.IResponse;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class AdvancedCallback implements IAdvancedCallBack {

    private final Path path;
    private final Method method;
    private final AdvancedRequest parent;

    @Override
    public void handle(@NonNull IResponse response) {
        int argsCount = method.getParameterCount();

        if(argsCount > 1) throw new IllegalArgumentException("Method " + method.getName() + " has too many arguments");
        Object[] args = new Object[argsCount];

        if(argsCount == 1 && method.getParameterTypes()[0] == IResponse.class) {
            args[0] = response;
        } else if(argsCount == 1) {
            throw new IllegalArgumentException("Method " + method.getName() + " has an invalid argument");
        }

        invoke(args);
    }

    private void invoke(Object... args) {
        try {
            method.invoke(parent, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Type getType() {
        return path.type();
    }

    @Override
    public String getPath() {
        return path.value();
    }
}
