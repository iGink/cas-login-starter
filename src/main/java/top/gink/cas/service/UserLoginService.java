package top.gink.cas.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gink
 * @create 2023/7/10 14:46
 */
public interface UserLoginService {
    boolean checkLoginState(HttpServletRequest request);
}
