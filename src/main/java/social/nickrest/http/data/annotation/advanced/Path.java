package social.nickrest.http.data.annotation.advanced;

import social.nickrest.http.data.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see social.nickrest.http.data.annotation.advanced.BaseEndPoint
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Path {
    String value();
    Type type();
}
