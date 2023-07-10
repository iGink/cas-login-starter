package top.gink.cas.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.gink.cas.anno.IgnoreCasLogin;
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
        if (!(handler instanceof HandlerMethod)) {
            log.trace("cas login,url:{},not HandlerMethod", request.getRequestURI());
            return true;
        }
        HandlerMethod hm = (HandlerMethod) handler;
        //是否有忽略的注解
        IgnoreCasLogin ignoreCasLogin = AnnotationUtils.findAnnotation(hm.getMethod(), IgnoreCasLogin.class);
        if (ignoreCasLogin != null) {
            log.trace("cas login,url:{},ignore IgnoreCasLogin", request.getRequestURI());
            return true;
        }
        if (!userLoginService.isLoginState(request)) {
            throw new AuthException(CustError.NEED_LOGIN);
        }
        return true;
    }
}
