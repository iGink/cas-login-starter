package top.gink.cas.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "cas")
public class CasProperties {
    /**
     * 是否启用
     */
    public Boolean enabled;
    /**
     * 登陆成功地址
     */
    public String loginSuccessUrl;

    /**
     * 基础url
     */
    public String baseUrl;
    /**
     * 回调url
     */
    public String callbackUrl;
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
     * 忽略路径
     */
    public List<String> ignorePath;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getServiceValidateUrl() {
        return serviceValidateUrl;
    }

    public void setServiceValidateUrl(String serviceValidateUrl) {
        this.serviceValidateUrl = serviceValidateUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public List<String> getIgnorePath() {
        return ignorePath;
    }

    public void setIgnorePath(List<String> ignorePath) {
        this.ignorePath = ignorePath;
    }
}
