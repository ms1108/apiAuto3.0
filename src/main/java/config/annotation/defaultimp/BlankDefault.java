package config.annotation.defaultimp;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Blank;
import lombok.SneakyThrows;

public class BlankDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        Blank annotation = (Blank) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",空格测试,传入空格";
        String value = " ";
        annotationTestEntity.value = value;
        annotationTestEntity.des = des;
        commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());

        des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",首末尾空格测试,传入值:";
        value = " " + annotationTestEntity.field.get(annotationTestEntity.baseCaseData) + " ";
        annotationTestEntity.value = value;
        annotationTestEntity.des = des;
        commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());
    }
}
