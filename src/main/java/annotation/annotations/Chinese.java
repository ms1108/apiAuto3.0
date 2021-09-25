package annotation.annotations;

import annotation.IAnnotationTestMethod;
import config.annotation.defaultimp.ChineseDefault;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;

/**
 * 中文测试，输入中文
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface Chinese {

    int chineseLen() default 5;

    Class<? extends AssertMethod> asserts() default SuccessAssertDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default ChineseDefault.class;

}
