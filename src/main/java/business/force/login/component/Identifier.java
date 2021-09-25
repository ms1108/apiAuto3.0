package business.force.login.component;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.annotations.*;
import base.ApiMethod;
import base.BaseCase;
import config.after.SetIdentify;
import config.header.FormHeadersDefault;
import datastore.DataStore;
import depedchain.login.GetSsoTicketChain;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.List;

@Data
@Api(hostKey = "", value = "", apiMethod = ApiMethod.POST_FORM)
@Depend(value = GetSsoTicketChain.class)
@After(SetIdentify.class)
@Header(FormHeadersDefault.class)
public class Identifier extends BaseCase {
    @NotEmpty
    private String user_name = "";
    @NotNull
    private String user_pwd_md5 = "";
    private String ggcode = "123456";
    private String loginType = "";
    private String appid = DataStore.getRequestValue(GetSsoTicket.class, "");
    private String ssoTicket = DataStore.getResponseValue(GetSsoTicket.class, "");
    private String from = "";
    private String uclang = "sc";
    private String client_type = "pc";
    private String dlp_info = "";

    @Description("app端登陆")
    @AutoTest
    public Identifier clientTypeApp() {
        client_type = "app";
        return this;
    }

    //调试注解测试
    @SneakyThrows
    public static void main(String[] args) {
        //注解测试
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        Class<? extends BaseCase> BaseCaseClass = (Class<? extends BaseCase>) Class.forName(className);

        AnnotationServer annotationServer = new AnnotationServer();
        List<AnnotationTestEntity> annotationTestEntities = annotationServer.createAnnotationTestEntity(BaseCaseClass);

        if (annotationTestEntities != null && annotationTestEntities.size() > 0) {
            //只想执行某个注解可以这么写，想执行全部则注释掉这个过滤
            //annotationTestEntities = annotationTestEntities.stream()
            //        .filter(x -> x.annotation.annotationType().getSimpleName().equals(AutoTest.class.getSimpleName()))
            //        .collect(Collectors.toList());
            //第一个对象必须执行依赖测试
            annotationTestEntities.get(0).setExecuteDataDependClass(true);

            for (AnnotationTestEntity testEntity : annotationTestEntities) {
                annotationServer.executeAnnotationTest(testEntity);
            }
        }

    }
}
