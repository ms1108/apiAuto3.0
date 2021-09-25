package annotation;

import annotation.annotations.*;
import api.ApiTest;
import base.BaseCase;
import depedchain.DependChain;
import lombok.SneakyThrows;
import utils.ClassFinderUtil;
import utils.ReportUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationServer extends ApiTest {

    private String rootPath = "";
    private boolean executeDataDependClass;

    public List<AnnotationTestEntity> createAnnotationTestEntity(String packageName) {
        List<Class<? extends BaseCase>> baseCaseClasss = getBaseCaseClass(packageName);
        return createAnnotationTestEntity(baseCaseClasss);
    }

    //传入单个class类做调试用
    @SafeVarargs
    public final List<AnnotationTestEntity> createAnnotationTestEntity(Class<? extends BaseCase>... baseCaseClass) {
        if (baseCaseClass.length == 0) {
            System.out.println("需要传入继承了BaseCase的类");
            return null;
        }
        return createAnnotationTestEntity(new ArrayList<>(Arrays.asList(baseCaseClass)));
    }

    public List<AnnotationTestEntity> createAnnotationTestEntity(List<Class<? extends BaseCase>> baseCaseClasss) {
        List<AnnotationTestEntity> annotationTestEntities = new ArrayList<>();
        for (Class<? extends BaseCase> baseCaseClass : baseCaseClasss) {
            Class<? extends DependChain>[] dataDependClass = null;
            List<Method> baseCaseDataMethods = new ArrayList<>();
            List<Method> autoTestMethods = new ArrayList<>();
            List<Method> multiRequestMethods = new ArrayList<>();
            executeDataDependClass = true;
            //保存依赖链
            if (baseCaseClass.isAnnotationPresent(Depend.class)) {
                dataDependClass = baseCaseClass.getAnnotation(Depend.class).value();
            }
            //保存需要到的方法
            for (Method method : baseCaseClass.getDeclaredMethods()) {
                String currentClassName = method.getDeclaringClass().getSimpleName();
                //这些类不进行遍历
                if (currentClassName.equals(BaseCase.class.getSimpleName())
                        || currentClassName.equals(Object.class.getSimpleName())
                        || currentClassName.equals(ApiTest.class.getSimpleName())) {
                    continue;
                }
                if (method.isAnnotationPresent(BaseCaseData.class)) {
                    baseCaseDataMethods.add(method);
                }
                if (method.isAnnotationPresent(AutoTest.class)) {
                    autoTestMethods.add(method);
                }
                if (method.isAnnotationPresent(MultiRequest.class)) {
                    multiRequestMethods.add(method);
                }
            }
            Field[] fields = baseCaseClass.getDeclaredFields();
            fieldAnnotation(baseCaseClass, fields, dataDependClass, baseCaseDataMethods, annotationTestEntities);

            //处理方法上的注解测试
            for (Method method : autoTestMethods) {
                AutoTest annotation = method.getAnnotation(AutoTest.class);
                AnnotationTestEntity annotationTestEntity = new AnnotationTestEntity();
                annotationTestEntity.method = method;
                annotationTestEntity.dataDependClass = dataDependClass;
                annotationTestEntity.annotation = annotation;
                annotationTestEntity.baseCaseClass = baseCaseClass;
                annotationTestEntity.iAnnotationTestMethod = annotation.testMethod();
                annotationTestEntity.description = method.isAnnotationPresent(Description.class) ? method.getAnnotation(Description.class).value() : null;
                annotationTestEntity.executeDataDependClass = executeDataDependClass;
                //修改executeDataDependClass为注解中携带的值，之后的实体是否执行依赖由注解的值决定
                if (baseCaseClass.isAnnotationPresent(Depend.class))
                    executeDataDependClass = baseCaseClass.getAnnotation(Depend.class).isAlwaysExecute();
                annotationTestEntities.add(annotationTestEntity);
            }
            for (Method method : multiRequestMethods) {
                MultiRequest annotation = method.getAnnotation(MultiRequest.class);
                AnnotationTestEntity annotationTestEntity = new AnnotationTestEntity();
                annotationTestEntity.method = method;
                annotationTestEntity.dataDependClass = dataDependClass;
                annotationTestEntity.annotation = annotation;
                annotationTestEntity.baseCaseClass = baseCaseClass;
                annotationTestEntity.iAnnotationTestMethod = annotation.testMethod();
                annotationTestEntity.executeDataDependClass = executeDataDependClass;
                //修改executeDataDependMethod为注解中携带的值，之后的实体是否执行依赖由注解的值决定
                if (baseCaseClass.isAnnotationPresent(Depend.class))
                    executeDataDependClass = baseCaseClass.getAnnotation(Depend.class).isAlwaysExecute();
                annotationTestEntities.add(annotationTestEntity);

            }
        }
        return annotationTestEntities;
    }

    public void fieldAnnotation(Class<? extends BaseCase> baseCaseClass, Field[] fields, Class<? extends DependChain>[] dataDependClass, List<Method> baseCaseDataMethods, List<AnnotationTestEntity> annotationTestEntities) {
        for (Field field : fields) {
            String currentClassName = field.getDeclaringClass().getSimpleName();
            //这些类不进行遍历
            if (currentClassName.equals(BaseCase.class.getSimpleName())
                    || currentClassName.equals(Object.class.getSimpleName())
                    || currentClassName.equals(ApiTest.class.getSimpleName())) {
                continue;
            }
            if (field.isAnnotationPresent(Blank.class)) {
                Blank annotation = field.getAnnotation(Blank.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(Chinese.class)) {
                Chinese annotation = field.getAnnotation(Chinese.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(EnumInt.class)) {
                EnumInt annotation = field.getAnnotation(EnumInt.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(EnumString.class)) {
                EnumString annotation = field.getAnnotation(EnumString.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(IntToString.class)) {
                IntToString annotation = field.getAnnotation(IntToString.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(Length.class)) {
                Length annotation = field.getAnnotation(Length.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(NotEmpty.class)) {
                NotEmpty annotation = field.getAnnotation(NotEmpty.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(NotNull.class)) {
                NotNull annotation = field.getAnnotation(NotNull.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(Range.class)) {
                Range annotation = field.getAnnotation(Range.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(Search.class)) {
                Search annotation = field.getAnnotation(Search.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(SpecialCharacters.class)) {
                SpecialCharacters annotation = field.getAnnotation(SpecialCharacters.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(StringToInt.class)) {
                StringToInt annotation = field.getAnnotation(StringToInt.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            if (field.isAnnotationPresent(Unique.class)) {
                Unique annotation = field.getAnnotation(Unique.class);
                annotationTestEntities.addAll(fieldHandle(baseCaseClass, field, annotation.group(), dataDependClass, baseCaseDataMethods, annotation, annotation.testMethod()));
            }
            //getType()含有$说明含有内部类
            if (field.getType().toString().contains("$")) {
                rootPath = rootPath + field.getName() + ".";
                inertClass(baseCaseClass, dataDependClass, baseCaseDataMethods, annotationTestEntities, field.getType().getSimpleName());
            }
            rootPath = "";
        }
    }

    //获取与字段同组下的baseCaseData
    public List<AnnotationTestEntity> fieldHandle(Class<? extends BaseCase> baseCaseClass, Field field, String[] fieldGroups, Class<? extends DependChain>[] dataDependClass, List<Method> baseCaseDataMethods, Annotation annotation, Class<? extends IAnnotationTestMethod> iAnnotationTestMethod) {
        List<AnnotationTestEntity> fieldAndMethodSameGroupEntities = new ArrayList<>();
        //存储与字段同组下的baseCaseData
        List<Method> baseCaseDataMethodSameGroup = new ArrayList<>();
        //字段所属的组
        List<String> groupList = Arrays.asList(fieldGroups);
        for (Method method : baseCaseDataMethods) {
            BaseCaseData baseCaseData = method.getAnnotation(BaseCaseData.class);
            String methodGroup = baseCaseData.group();
            if (groupList.contains(methodGroup)) {
                baseCaseDataMethodSameGroup.add(method);
            } else if (groupList.contains("0")) {//如果字段属于0号组，说明所有的组都需要该字段
                baseCaseDataMethodSameGroup.add(method);
            }
        }

        for (Method method : baseCaseDataMethodSameGroup) {
            AnnotationTestEntity annotationTestEntity = new AnnotationTestEntity();
            annotationTestEntity.annotation = annotation;
            annotationTestEntity.iAnnotationTestMethod = iAnnotationTestMethod;
            annotationTestEntity.field = field;
            annotationTestEntity.fieldPath = rootPath + field.getName();
            annotationTestEntity.dataDependClass = dataDependClass;
            annotationTestEntity.baseCaseDataMethod = method;
            annotationTestEntity.baseCaseClass = baseCaseClass;
            //第一个实体一定记录的是执行依赖，切换另一个BaseCase类则executeDataDependClass被重新赋值为true
            annotationTestEntity.executeDataDependClass = executeDataDependClass;
            //修改executedataDependClass为注解中携带的值，之后的实体是否执行依赖由注解的值决定
            if (baseCaseClass != null && baseCaseClass.isAnnotationPresent(Depend.class))
                executeDataDependClass = baseCaseClass.getAnnotation(Depend.class).isAlwaysExecute();
            fieldAndMethodSameGroupEntities.add(annotationTestEntity);
        }
        //处理没有基础数据注解的类，基础数据为该类的无参初始化
        if (baseCaseDataMethodSameGroup.size() == 0) {
            AnnotationTestEntity annotationTestEntity = new AnnotationTestEntity();
            annotationTestEntity.annotation = annotation;
            annotationTestEntity.iAnnotationTestMethod = iAnnotationTestMethod;
            annotationTestEntity.field = field;
            annotationTestEntity.fieldPath = rootPath + field.getName();
            annotationTestEntity.dataDependClass = dataDependClass;
            annotationTestEntity.baseCaseClass = baseCaseClass;
            //第一个实体一定记录的是执行依赖，切换另一个BaseCase类则executedataDependClass被重新赋值为true
            annotationTestEntity.executeDataDependClass = executeDataDependClass;
            //修改executedataDependClass为注解中携带的值，之后的实体是否执行依赖由注解的值决定
            if (baseCaseClass != null && baseCaseClass.isAnnotationPresent(Depend.class))
                executeDataDependClass = baseCaseClass.getAnnotation(Depend.class).isAlwaysExecute();
            fieldAndMethodSameGroupEntities.add(annotationTestEntity);
        }
        return fieldAndMethodSameGroupEntities;
    }

    private void inertClass(Class<? extends BaseCase> baseCaseClass, Class<? extends DependChain>[] dataDependClass, List<Method> baseCaseDataMethods, List<AnnotationTestEntity> annotationTestEntities, String className) {
        inertClassHandle(baseCaseClass, baseCaseClass, dataDependClass, baseCaseDataMethods, annotationTestEntities, className);
    }

    private void inertClassHandle(Class<? extends BaseCase> baseCaseClass, Class<?> clazz, Class<? extends DependChain>[] dataDependClass, List<Method> baseCaseDataMethods, List<AnnotationTestEntity> annotationTestEntities, String className) {
        Class<?>[] innerClazz = clazz.getClasses();
        for (Class claszInner : innerClazz) {
            if (className.equals(claszInner.getSimpleName())) {
                Field[] fields = claszInner.getDeclaredFields();
                fieldAnnotation(baseCaseClass, fields, dataDependClass, baseCaseDataMethods, annotationTestEntities);
            }
            if (claszInner.getClasses().length > 0) {
                inertClassHandle(baseCaseClass, claszInner, dataDependClass, baseCaseDataMethods, annotationTestEntities, className);
            }
        }
    }

    public List<Class<? extends BaseCase>> getBaseCaseClass(String scannedPackage) {
        ClassFinderUtil classFinderUtil = new ClassFinderUtil();
        return classFinderUtil.scanBaseCaseClass(scannedPackage);
    }

    @SneakyThrows
    public void executeAnnotationTest(AnnotationTestEntity annotationTestEntity) {
        //执行依赖方法
        Class<? extends DependChain>[] dataDependClass = annotationTestEntity.dataDependClass;
        if (dataDependClass != null && annotationTestEntity.executeDataDependClass) {
            for (Class<? extends DependChain> dependClass : dataDependClass) {
                ReportUtil.log("DataDependClass   : " + dependClass.getName());
                DependChain dependChain = dependClass.newInstance();
                BaseCase depend = dependChain.depend();
                dependChain.invokeDepend(depend);
            }
        }
        //执行BaseCaseData
        Method baseCaseDataMethod = annotationTestEntity.baseCaseDataMethod;
        BaseCase baseCaseData;
        if (baseCaseDataMethod != null) {
            //有的依赖数据需要执行了依赖方法才能获得，所以对象要放在后边初始化
            baseCaseData = (BaseCase) baseCaseDataMethod.invoke(annotationTestEntity.baseCaseClass.newInstance());
        } else {//不存在baseCaseData则用初始对象
            baseCaseData = annotationTestEntity.baseCaseClass.newInstance();
        }
        annotationTestEntity.baseCaseData = baseCaseData;
        IAnnotationTestMethod instance = annotationTestEntity.iAnnotationTestMethod.newInstance();
        instance.testMethod(annotationTestEntity);
    }
}