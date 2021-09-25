package business.force.login.component;

import annotation.annotations.Api;
import annotation.annotations.Depend;
import annotation.annotations.Header;
import base.ApiMethod;
import base.BaseCase;
import config.header.FormHeadersDefault;
import datastore.DataStore;
import depedchain.login.GetSsoUrlChain;
import lombok.Data;


@Data
@Depend(GetSsoUrlChain.class)
@Api(hostKey = "", value = "", apiMethod = ApiMethod.POST_FORM, des = "GetSsoTicket")
@Header(FormHeadersDefault.class)
public class GetSsoTicket extends BaseCase {

    private String appid;

    private String callback = DataStore.getRequestValue(GetSsoUrl.class, "args.callback");

    private String _t;

    private String _sign;

    public GetSsoTicket() {
        String ssoUrl = DataStore.getResponseValue(GetSsoUrl.class, "data.sso_url");
        if (ssoUrl != null) {
            String[] result = ssoUrl.split("\\&");
            for (String s : result) {
                if (s.startsWith("appid")) {
                    appid = s.split("=")[1];
                } else if (s.startsWith("_t")) {
                    _t = s.split("=")[1];
                } else if (s.startsWith("_sign")) {
                    _sign = s.split("=")[1];
                }
            }
        }
    }

}
