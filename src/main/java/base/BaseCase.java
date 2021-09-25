package base;

import config.asserts.AssertMethod;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public abstract class BaseCase{
    //拼接路径参数适用与restful风格
    public String pathParam;//开头需要携带斜杠'/'

    //断言的方式
    public AssertMethod assertMethod;

    //根据模块赋默认的实现对象
    @SneakyThrows
    public BaseCase() {
        String packageName = this.getClass().getPackage().getName();
        BusinessDefaultInfo defaultImplEnum = BusinessDefaultInfo.getModuleEnum(packageName);
        assertMethod = defaultImplEnum.getAssertMethod().newInstance();
    }

}
