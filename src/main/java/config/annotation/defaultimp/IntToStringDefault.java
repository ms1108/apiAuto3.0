package config.annotation.defaultimp;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.IntToString;
import lombok.SneakyThrows;

public class IntToStringDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        IntToString annotation = (IntToString) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",IntToString类型测试,传入字符:";
        String value = annotationTestEntity.field.get(annotationTestEntity.baseCaseData) + "";
        annotationTestEntity.value = value;
        annotationTestEntity.des = des + value;
        commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
