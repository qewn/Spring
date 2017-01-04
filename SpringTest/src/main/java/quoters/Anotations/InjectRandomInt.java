package quoters.Anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by qen on 01.01.2017.
 */
@Retention(RetentionPolicy.RUNTIME) // Щоб було видно в runtime і можна було через рефлекцію зчитати
public @interface InjectRandomInt {
    int min();
    int max();
}
