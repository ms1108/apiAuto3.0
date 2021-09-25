package business.force.login;

import annotation.AnnotationTest;
import base.BaseCase;
import business.force.login.component.GetSsoTicket;
import business.force.login.component.GetSsoUrl;
import business.force.login.component.Identifier;
import depedchain.login.GetSsoUrlChain;
import org.testng.annotations.Test;

import static base.InvokeDependNewInstance.invokeDependNewInstance;
import static datastore.DataStore.putDependChainDIY;

public class LoginTest extends AnnotationTest {

    @Test
    public void login() {
        apiTest(new GetSsoUrl());
        apiTest(new GetSsoTicket());
        apiTest(new Identifier());
    }

    //执行对应的用例
    @Test
    public void loginClientTypeApp() {
        apiTest(invokeDependNewInstance(Identifier.class).clientTypeApp());
    }

    //修改调用链的任意入参
    @Test
    public void loginChangeChain() {
        putDependChainDIY(GetSsoUrlChain.class, new GetSsoUrlChain() {
            @Override
            public BaseCase depend() {
                GetSsoUrl getSsoUrl = invokeDependNewInstance(GetSsoUrl.class);
                getSsoUrl.args.callback = "";
                apiTest(getSsoUrl);
                return null;
            }
        });
        apiTest(invokeDependNewInstance(Identifier.class));
    }
}
