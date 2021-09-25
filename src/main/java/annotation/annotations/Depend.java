package annotation.annotations;

import depedchain.DependChain;
import depedchain.DependChainChange;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
public @interface Depend {
    Class<? extends DependChain>[] value() default DependChain.class;

    //该信息定义了是否在每个字段注解测试时都执行一遍DataDepend标记的方法
    boolean isAlwaysExecute() default false;

    //将自定义的对象放入调用链中
    Class<? extends DependChainChange>[] putDependChainDIY() default DependChainChange.class;
}
