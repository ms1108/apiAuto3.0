package annotation.annotations;

import config.header.IHeaders;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
//请求头
public @interface Header {
    Class<? extends IHeaders> value();
}
