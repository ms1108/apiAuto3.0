package business.test.mytest.component;

import annotation.annotations.*;
import base.ApiMethod;
import base.BaseCase;
import config.asserts.SuccessAssertDefault;
import lombok.Data;
import lombok.experimental.Accessors;
import utils.RandomUtil;

import static utils.InjectJsonFileUtil.injectJsonFile;
import static utils.set.PropertiesUtil.get;

@Data
@Accessors(fluent = true)
@Api(hostKey = "", value = "", apiMethod = ApiMethod.POST_FORM)
public class AddDataCase extends BaseCase {

    //@Unique(assertFail = SuccessAssertDefault.class, group = "1")
    //@EnumString
    //@NotNull(asserts = SuccessAssertDefault.class)
    //@NotEmpty(asserts = SuccessAssertDefault.class)
    //@Blank(assertFail = SuccessAssertDefault.class)
    public String name = "name" + RandomUtil.getString(10);


    //@Length(minLen = 1, maxLen = 8, assertFail = SuccessAssertDefault.class)
    public String pwd = get("g_loginPwd");

    //@EnumInt
    public Integer isManage;

    public Type type = new Type();

    public String depend = "123";//依赖config接口返回的结果

    public String userName = RandomUtil.getString();
    public String test;

    @Data
    public static class Type {
        //@Range(maxNum = "10", minInfinite = true, assertFail = SuccessAssertDefault.class)
        //@Unique(assertFail = SuccessAssertDefault.class)
        public TypeIn role = new TypeIn();

        @Data
        public static class TypeIn {
            //@Range(minNum = "0.1", maxNum = "1", floatValue = "0.1", assertFail = SuccessAssertDefault.class)//测试范围(0,1]
            //@EnumInt
            //@EnumString
            @NotEmpty(asserts = SuccessAssertDefault.class)
            @Blank(assertFail = SuccessAssertDefault.class)
            @NotNull(asserts = SuccessAssertDefault.class)
            public Integer TypeIn = 1;
        }
    }

    @AutoTest
    public AddDataCase testInject() {
        AddDataCase addDataCase = injectJsonFile(this.getClass(), "addData.json");
        addDataCase.isManage = 2;
        return addDataCase;
    }

    @BaseCaseData
    public AddDataCase testInject1() {
        AddDataCase addDataCase = testInject();
        addDataCase.depend = "111";
        return addDataCase;
    }

    public static void main(String[] args) {
        AddDataCase addDataCase = new AddDataCase().testInject1();
        System.out.println(addDataCase);
    }


}
