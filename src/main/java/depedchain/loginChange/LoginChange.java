package depedchain.loginChange;

import base.BaseCase;
import business.force.login.component.GetSsoUrl;
import business.force.login.component.Identifier;
import com.alibaba.fastjson.JSONObject;
import depedchain.DependChainChange;
import depedchain.login.GetSsoUrlChain;
import depedchain.login.IdentifyChain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static base.InvokeDependNewInstance.invokeDependNewInstance;
import static datastore.DataStore.putDependChainDIY;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginChange extends DependChainChange {
    private String callback = "";
    private String user_name = "";
    private String user_pwd_md5 = "";

    @Override
    public void put() {
        putDependChainDIY(GetSsoUrlChain.class, new GetSsoUrlChain() {
            @Override
            public BaseCase depend() {
                BaseCase depend = super.depend();
                GetSsoUrl getSsoUrl1 = JSONObject.parseObject(depend.toString(), GetSsoUrl.class);
                GetSsoUrl getSsoUrl = invokeDependNewInstance(GetSsoUrl.class);
                getSsoUrl.args.callback = callback;
                return getSsoUrl;
            }
        });
        putDependChainDIY(IdentifyChain.class, new IdentifyChain() {
            @Override
            public BaseCase depend() {
                Identifier identifier = invokeDependNewInstance(Identifier.class);
                identifier.setUser_name(user_name);
                identifier.setUser_pwd_md5(user_pwd_md5);
                return identifier;
            }
        });
    }
}
