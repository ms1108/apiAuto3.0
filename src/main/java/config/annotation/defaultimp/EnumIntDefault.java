package config.annotation.defaultimp;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.EnumInt;
import lombok.SneakyThrows;

public class EnumIntDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        EnumInt annotation = (EnumInt) annotationTestEntity.annotation;
        String des;
        for (int iEnum : annotation.value()) {
            des =
                    "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                            ",字段名:" + annotationTestEntity.field.getName() +
                            ",int类型枚举遍历测试,传入：";
            annotationTestEntity.value = iEnum;
            annotationTestEntity.des =des + iEnum;
            commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());
        }
    }
}
