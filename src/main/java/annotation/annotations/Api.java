package annotation.annotations;

import base.ApiMethod;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
public @interface Api {
    //host在配置文件中的key
    String hostKey() default "";
    //接口路径,必填
    String value();
    //请求方式
    ApiMethod apiMethod() default ApiMethod.PLACEHOLDER;
    //jsonSchema文件位置如jsonschema/test/test.json
    String jsonSchemaPath() default "";
    //接口描述
    String des() default "";
}
