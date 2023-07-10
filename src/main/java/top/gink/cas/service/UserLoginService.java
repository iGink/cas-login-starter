package top.gink.cas.service;

import top.gink.cas.model.CasResp;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gink
 * @create 2023/7/10 14:46
 */
public interface UserLoginService {
    boolean isLoginState(HttpServletRequest request);

    void clearUserInfo(HttpServletRequest request);

    void setUserInfo(HttpServletRequest request, String ticket, CasResp casResp);
}
