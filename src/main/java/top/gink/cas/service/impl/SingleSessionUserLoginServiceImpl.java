package top.gink.cas.service.impl;

import org.apache.commons.lang3.StringUtils;
import top.gink.cas.model.CasResp;
import top.gink.cas.service.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static top.gink.cas.constant.Constant.AUTH_USER_NAME;

/**
 * @author Gink
 * @create 2023/7/10 14:49
 */
public class SingleSessionUserLoginServiceImpl implements UserLoginService {
    @Override
    public boolean isLoginState(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(AUTH_USER_NAME);
        if (attribute == null) {
            return false;
        }
        String userName = attribute.toString();
        if (StringUtils.isBlank(userName)) {
            return false;
        }
        return true;
    }

    @Override
    public void clearUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(AUTH_USER_NAME);
    }

    @Override
    public void setUserInfo(HttpServletRequest request, String ticket, CasResp casResp) {
        HttpSession session = request.getSession();
        String userName = casResp.serviceResponse.authenticationSuccess.user;
        session.setAttribute(AUTH_USER_NAME, userName);
    }
}
