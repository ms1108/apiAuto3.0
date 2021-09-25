package annotation.annotations;

import annotation.IAnnotationTestMethod;
import config.annotation.defaultimp.SpecialCharacterDefault;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;
/**
 * 特殊字符测试
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface SpecialCharacters {
    String specialCharacter() default "`~!@#$%^&*()_-+=|\\{}[]:;\"'<>?,./，。、（）？￥’”\\n\\r\\t\\b\\f";

    String allowCharacters() default "";

    String denyCharacters() default "";

    Class<? extends AssertMethod> assertsSuccess() default SuccessAssertDefault.class;

    Class<? extends AssertMethod> assertFail() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default SpecialCharacterDefault.class;
}
