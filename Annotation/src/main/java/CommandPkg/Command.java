package CommandPkg;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Command {

}
