package annotation.annotations;

import config.after.IAfter;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
//后置处理器
public @interface After {
    Class<? extends IAfter> value();
}
