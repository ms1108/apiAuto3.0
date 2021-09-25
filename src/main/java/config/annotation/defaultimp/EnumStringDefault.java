package config.annotation.defaultimp;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import lombok.SneakyThrows;

public class EnumStringDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        annotation.annotations.EnumString annotation = (annotation.annotations.EnumString) annotationTestEntity.annotation;
        String des;
        for (String iEnum : annotation.value()) {
            des =
                    "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                            ",字段名:" + annotationTestEntity.field.getName() +
                            ",String类型枚举遍历测试,传入：";
            annotationTestEntity.value = iEnum;
            annotationTestEntity.des = des + iEnum;
            commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());
        }
    }
}
