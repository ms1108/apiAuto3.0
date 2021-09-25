package base;

import annotation.annotations.Depend;
import depedchain.DependChain;
import depedchain.DependChainChange;
import lombok.SneakyThrows;

import static datastore.TreadLocalStore.dataStore;

public class InvokeDependNewInstance {
    //在数据依赖中调用其他接口时使用该方法进行new对象，可实现动态修改依赖的实现
    @SneakyThrows
    public static <T> T invokeDependNewInstance(Class<T> baseCaseClass) {
        Class<? extends BaseCase> aClass = (Class<? extends BaseCase>) baseCaseClass;
        if (aClass.isAnnotationPresent(Depend.class)) {
            Depend annotation = aClass.getAnnotation(Depend.class);
            //注解中putDependChainDIY调用方法将DependChain对象放入调用链中,如果第一个是DependChainChange则说明putDependChainDIY没有赋值
            if (!annotation.putDependChainDIY()[0].getName().equals(DependChainChange.class.getName())) {
                for (Class<? extends DependChainChange> dependChainChangeClass : annotation.putDependChainDIY()) {
                    dependChainChangeClass.newInstance().put();
                }
            }
            //获取修改的调用链
            Class<? extends DependChain>[] dependChainClasss = annotation.value();
            for (Class<? extends DependChain> chainClass : dependChainClasss) {
                if (dataStore.get().dependChainDIY.get(chainClass.getName()) != null) {
                    //调用自定义调用链依赖方法
                    DependChain dependChain = (DependChain) dataStore.get().dependChainDIY.get(chainClass.getName());
                    BaseCase depend = dependChain.depend();
                    dependChain.invokeDepend(depend);
                } else {
                    DependChain dependChain = chainClass.newInstance();
                    BaseCase depend = dependChain.depend();
                    dependChain.invokeDepend(depend);
                }
            }
        }
        return (T) aClass.newInstance();
    }
}
