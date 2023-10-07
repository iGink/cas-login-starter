package top.gink.cas.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "cas")
public class CasProperties {
    /**
     * 是否启用
     */
    public boolean enabled;
    /**
     * 登陆成功地址
     */
    public String loginSuccessUrl;

    /**
     * 基础url
     */
    public String baseUrl;
    /**
     * 服务端url
     */
    public String serverUrl;
    /**
     * 服务端验证url
     */
    public String serviceValidateUrl;
    /**
     * 登录url
     */
    public String loginUrl;
    /**
     * 登出url
     */
    public String logoutUrl;
    /**
     * 回调url
     */
    public String callbackUrl;
    /**
     * 忽略路径
     */
    public List<String> ignorePath;

}
