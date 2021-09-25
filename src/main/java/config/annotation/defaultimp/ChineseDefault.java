package config.annotation.defaultimp;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Chinese;
import lombok.SneakyThrows;
import utils.RandomUtil;

public class ChineseDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        Chinese annotation = (Chinese) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",中文字符测试,传入中文值:";
        String value = RandomUtil.getChinese(annotation.chineseLen());
        annotationTestEntity.value = value;
        annotationTestEntity.des = des + value;
        commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());
    }
}
