package top.gink.cas.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import top.gink.cas.exception.AuthException;
import top.gink.cas.exception.CustError;
import top.gink.cas.service.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CasAuthInterceptor implements HandlerInterceptor {
    private final UserLoginService userLoginService;

    public CasAuthInterceptor(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        log.trace("start cas login,url:{}", request.getRequestURI());
        if (!userLoginService.checkLoginState(request)) {
            throw new AuthException(CustError.NEED_LOGIN);
        }
        return true;
    }
}
