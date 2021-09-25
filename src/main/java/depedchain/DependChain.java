package depedchain;

import api.ApiTest;
import base.BaseCase;

public abstract class DependChain extends ApiTest {

    public abstract BaseCase depend();

    public abstract void invokeDepend(BaseCase baseCase);
}
