package top.gink.cas.service.impl;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import top.gink.cas.exception.ServiceException;
import top.gink.cas.model.*;
import top.gink.cas.service.CasReqService;

/**
 * @author Gink
 * @create 2023/7/10 16:21
 */
public class SimpleCasReqServiceImpl implements CasReqService {
    private final CasProperties casProperties;
    private final Gson gson = new Gson();

    public SimpleCasReqServiceImpl(CasProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Override
    public CasResp serviceValidate(String ticket) {
        String serviceValidateUrl = casProperties.serviceValidateUrl;
        String callbackUrl = casProperties.callbackUrl;
        String url = serviceValidateUrl + "?ticket=" + ticket + "&service=" + callbackUrl + "&format=json";
        HttpGet httpGet = new HttpGet(url);
        String resp;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            resp = httpclient.execute(httpGet, new BasicHttpClientResponseHandler());
        } catch (Exception e) {
            throw new ServiceException("Cas服务器请求异常");
        }
        CasResp casResp = gson.fromJson(resp, CasResp.class);
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
