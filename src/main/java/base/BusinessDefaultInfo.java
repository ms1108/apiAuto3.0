package base;

import config.after.AfterDefault;
import config.after.IAfter;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.before.BeforeDefault;
import config.before.IBefore;
import config.header.HeadersDefault;
import config.header.IHeaders;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
public enum BusinessDefaultInfo {
    LOGIN("force.login", "g_host_app_force", ApiMethod.POST, HeadersDefault.class, BeforeDefault.class, AfterDefault.class, SuccessAssertDefault.class),
    LOGIN1("test", "g_host_app_force", ApiMethod.POST, HeadersDefault.class, BeforeDefault.class, AfterDefault.class, SuccessAssertDefault.class);
    private String packageName;
    private String hostKey;
    private ApiMethod apiMethod;
    private Class<? extends IHeaders> iHeaders;
    private Class<? extends IBefore> iBefore;
    private Class<? extends IAfter> iAfter;
    private Class<? extends AssertMethod> assertMethod;
    private static Map<String, BusinessDefaultInfo> packageNameAndEnum = new HashMap<>();

    BusinessDefaultInfo(String packageName, String hostKey, ApiMethod apiMethod, Class<? extends IHeaders> iHeaders, Class<? extends IBefore> iBefore, Class<? extends IAfter> iAfter, Class<? extends AssertMethod> assertMethod) {
        this.packageName = packageName;
        this.hostKey = hostKey;
        this.apiMethod = apiMethod;
        this.iHeaders = iHeaders;
        this.iBefore = iBefore;
        this.iAfter = iAfter;
        this.assertMethod = assertMethod;
    }

    //模糊匹配，谁命中率高就返回谁
    public static BusinessDefaultInfo getModuleEnum(String packageName) {
        //获取历史记录的感觉
        if (packageNameAndEnum.get(packageName) != null) {
            return packageNameAndEnum.get(packageName);
        }
        //枚举存的包名，和传进来的包名做对比，那个看命中率高就返回那个枚举
        int similarity = 0;
        BusinessDefaultInfo possibleEnum = null;
        for (BusinessDefaultInfo value : BusinessDefaultInfo.values()) {
            List<String> enumPackageNames = Arrays.stream(value.getPackageName().split("\\.")).collect(Collectors.toList());
            List<String> packageNames = Arrays.stream(packageName.split("\\.")).collect(Collectors.toList());
            //求列表的交集，即公共部分
            enumPackageNames.retainAll(packageNames);
            //enumPackageNames.size();为命中个数
            if (enumPackageNames.size() > similarity) {
                similarity = enumPackageNames.size();
                possibleEnum = value;
            }
        }
        packageNameAndEnum.put(packageName, possibleEnum);
        return possibleEnum;
    }
}
