package depedchain.login;

import base.BaseCase;
import business.force.login.component.GetSsoUrl;
import depedchain.DependChain;

import static base.InvokeDependNewInstance.invokeDependNewInstance;

public class GetSsoUrlChain extends DependChain {

    @Override
    public BaseCase depend() {
        return invokeDependNewInstance(GetSsoUrl.class);
    }

    @Override
    public void invokeDepend(BaseCase baseCase) {
        apiTest(baseCase);
    }
}
