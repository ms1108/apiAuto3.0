package annotation.annotations;

import annotation.IAnnotationTestMethod;
import config.annotation.defaultimp.LengthDefault;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;
/**
 * 长度测试
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface Length {
    int maxLen() default 1;

    int minLen() default 1;

    Class<? extends AssertMethod> assertSuccess() default SuccessAssertDefault.class;

    Class<? extends AssertMethod> assertFail() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default LengthDefault.class;

}
