package social.nickrest.http.data.annotation;

import social.nickrest.http.data.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EndPoint {
    String path();
    Type type();
}
