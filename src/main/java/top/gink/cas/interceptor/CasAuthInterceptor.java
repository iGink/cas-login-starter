package top.gink.cas.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
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

public class CasAuthInterceptor implements HandlerInterceptor {
    private final UserLoginService userLoginService;
    private final CasProperties casProperties;
    private static final Logger log = LoggerFactory.getLogger(CasAuthInterceptor.class);

    public CasAuthInterceptor(UserLoginService userLoginService, CasProperties casProperties) {
        this.userLoginService = userLoginService;
        this.casProperties = casProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        log.trace("start cas login,url:{}", request.getRequestURI());
        //判断是否是HandlerMethod
        if (!(handler instanceof HandlerMethod)) {
            log.trace("cas login,url:{},not HandlerMethod", request.getRequestURI());
            return true;
        }
        HandlerMethod hm = (HandlerMethod) handler;
        //是否有忽略的注解
        IgnoreCasLogin i1 = AnnotationUtils.findAnnotation(hm.getMethod(), IgnoreCasLogin.class);
        IgnoreCasLogin i2 = AnnotationUtils.findAnnotation(hm.getBeanType(), IgnoreCasLogin.class);
        if (i1 != null || i2 != null) {
            log.trace("cas login,url:{},ignore IgnoreCasLogin", request.getRequestURI());
            return true;
        }
        //判断是否需要登录
        if (userLoginService.needLogin(request)) {
            return doLogin(response, hm);
        }
        return true;
    }

    /*------------------------------------------内部方法--------------------------------------------*/
    private boolean doLogin(HttpServletResponse response, HandlerMethod hm) throws IOException {
        // Check if the request body or the controller has the ResponseBody annotation
        ResponseBody requestBody = AnnotationUtils.findAnnotation(hm.getMethod(), ResponseBody.class);
        ResponseBody restController = AnnotationUtils.findAnnotation(hm.getBeanType(), ResponseBody.class);
        // If either of them has the ResponseBody annotation, redirect to the login page
        if (requestBody != null || restController != null) {
            response.sendRedirect(casProperties.loginUrl);
            return false;
            // Otherwise, throw an AuthException
        } else {
            throw new AuthException(CustError.NEED_LOGIN);
        }
    }
}
