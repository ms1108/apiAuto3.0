package business.force.login.component;

import annotation.annotations.Api;
import annotation.annotations.Before;
import base.BaseCase;
import config.before.SetIdentifierNull;
import lombok.Data;
import lombok.experimental.Accessors;
import utils.set.PropertiesUtil;

@Data
@Api("")
@Before(SetIdentifierNull.class)
public class GetSsoUrl extends BaseCase {
    public Args args = new Args();

    @Data
    public static class Args {
        public String type = "logout";
        public String callback = PropertiesUtil.get("g_host_callback_force");
    }

}
