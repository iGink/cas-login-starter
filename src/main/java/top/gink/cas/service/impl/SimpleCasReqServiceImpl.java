package top.gink.cas.service.impl;

import org.apache.commons.lang3.StringUtils;
import top.gink.cas.exception.ServiceException;
import top.gink.cas.model.*;
import top.gink.cas.service.CasReqService;
import top.gink.cas.util.okhttp.HttpUtil;

/**
 * @author Gink
 * @create 2023/7/10 16:21
 */
public class SimpleCasReqServiceImpl implements CasReqService {
    private final CasProperties casProperties;

    public SimpleCasReqServiceImpl(CasProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Override
    public CasResp serviceValidate(String ticket) {
        String serviceValidateUrl = casProperties.serviceValidateUrl;
        String callbackUrl = casProperties.callbackUrl;
        CasResp casResp = HttpUtil.sync(serviceValidateUrl)
                .addUrlPara("ticket", ticket)
                .addUrlPara("service", callbackUrl)
                .addUrlPara("format", "json")
                .get()
                .getBody()
                .toBean(CasResp.class);
        if (casResp == null || casResp.serviceResponse == null) {
            throw new ServiceException("Cas服务器响应异常");
        }
        ServiceResponse serviceResponse = casResp.serviceResponse;
        AuthenticationFailure failure = serviceResponse.authenticationFailure;
        if (failure != null) {
            throw new ServiceException(failure.code + failure.description);
        }
        AuthenticationSuccess success = serviceResponse.authenticationSuccess;
        if (success == null || StringUtils.isBlank(success.user)) {
            throw new ServiceException("cas无可靠响应");
        }
        return casResp;
    }
}
