package top.gink.cas.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.gink.cas.anno.IgnoreCasLogin;
import top.gink.cas.exception.AuthException;
import top.gink.cas.exception.CustError;
import top.gink.cas.model.CasProperties;
import top.gink.cas.service.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CasAuthInterceptor implements HandlerInterceptor {
    private final UserLoginService userLoginService;
    private final CasProperties casProperties;

    public CasAuthInterceptor(UserLoginService userLoginService, CasProperties casProperties) {
        this.userLoginService = userLoginService;
        this.casProperties = casProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
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
        if (userLoginService.needLogin(request)) {
            return doLogin(response, hm);
        }
        return true;
    }

    /*------------------------------------------内部方法--------------------------------------------*/

    /**
     * 尝试登录
     *
     * @param response
     * @param hm
     * @return
     * @throws IOException
     */
    private boolean doLogin(HttpServletResponse response, HandlerMethod hm) throws IOException {
        RequestBody requestBody = AnnotationUtils.findAnnotation(hm.getMethod(), RequestBody.class);
        if (requestBody != null) {
            response.sendRedirect(casProperties.loginUrl);
            return false;
        } else {
            throw new AuthException(CustError.NEED_LOGIN);
        }
    }
}
