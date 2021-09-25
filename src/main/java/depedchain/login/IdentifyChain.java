package depedchain.login;

import base.BaseCase;
import business.force.login.component.Identifier;
import depedchain.DependChain;

import static base.InvokeDependNewInstance.invokeDependNewInstance;

public class IdentifyChain extends DependChain {
    @Override
    public BaseCase depend() {
        return invokeDependNewInstance(Identifier.class);
    }

    @Override
    public void invokeDepend(BaseCase baseCase) {
        apiTest(baseCase);
    }
}
