package depedchain.login;

import base.BaseCase;
import business.force.login.component.GetSsoTicket;
import depedchain.DependChain;

import static base.InvokeDependNewInstance.invokeDependNewInstance;

public class GetSsoTicketChain extends DependChain {
    @Override
    public BaseCase depend() {
        return invokeDependNewInstance(GetSsoTicket.class);
    }

    @Override
    public void invokeDepend(BaseCase baseCase) {
        apiTest(baseCase);
    }
}
