package top.gink.cas.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import top.gink.cas.model.CasProperties;
import top.gink.cas.model.CasResp;
import top.gink.cas.service.CasReqService;
import top.gink.cas.service.UserLoginService;

import javax.servlet.http.HttpServletRequest;


/**
 * 测试cas登录
 *
 * @author Gink
 * @create 2022/11/11 13:42
 */
@Controller
@RequestMapping({"/cas/"})
@ConditionalOnProperty(prefix = "cas", name = "enable", value = "true", matchIfMissing = true)
public class CasController {
    private final CasProperties casProperties;
    private final CasReqService casReqService;
    private final UserLoginService userLoginService;

    public CasController(CasProperties casProperties, CasReqService casReqService, UserLoginService userLoginService) {
        this.casProperties = casProperties;
        this.casReqService = casReqService;
        this.userLoginService = userLoginService;
    }


    @GetMapping("callback")
    public ModelAndView callback(String ticket, HttpServletRequest request) {
        userLoginService.clearUserInfo(request);//如果登录失败，清除掉之前登录的人的记录，因为cas模式没法从我这里发起，所以在回调的时候清理用户信息
        CasResp casResp = casReqService.serviceValidate(ticket);
        userLoginService.setUserInfo(request, ticket, casResp);

        RedirectView rdv = new RedirectView(casProperties.loginSuccessUrl);
        rdv.setExposeModelAttributes(false);
        return new ModelAndView(rdv);
    }

    @RequestMapping({"logout"})
    public ModelAndView callback(HttpServletRequest request) {
        userLoginService.clearUserInfo(request);

        String logoutUrl = casProperties.logoutUrl;
        RedirectView rdv = new RedirectView(logoutUrl);
        rdv.setExposeModelAttributes(false);
        return new ModelAndView(rdv);
    }


}
