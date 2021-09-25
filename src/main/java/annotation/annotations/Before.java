package annotation.annotations;

import config.before.IBefore;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
//前置处理器
public @interface Before {
    Class<? extends IBefore> value();
}
