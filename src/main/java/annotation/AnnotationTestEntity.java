package annotation;

import annotation.annotations.Description;
import base.BaseCase;
import depedchain.DependChain;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class AnnotationTestEntity {
    public Field field;
    public String fieldPath;
    public Class<? extends DependChain>[] dataDependClass;
    public Method baseCaseDataMethod;
    public Class<? extends BaseCase> baseCaseClass;
    public BaseCase baseCaseData;
    public Annotation annotation;
    //是否在每个注解测试都执行一次
    public boolean executeDataDependClass;
    public Class<? extends IAnnotationTestMethod> iAnnotationTestMethod;
    public Method method;
    //替换成什么值
    public Object value;
    //操作描述
    public String des;
    //Description注解信息
    public String description;

}
