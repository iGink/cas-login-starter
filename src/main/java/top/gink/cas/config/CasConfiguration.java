package top.gink.cas.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.gink.cas.interceptor.CasAuthInterceptor;
import top.gink.cas.model.CasProperties;
import top.gink.cas.service.CasReqService;
import top.gink.cas.service.UserLoginService;
import top.gink.cas.service.impl.SimpleCasReqServiceImpl;
import top.gink.cas.service.impl.SingleSessionUserLoginServiceImpl;

/**
 * @author Gink
 * @create 2023/7/10 14:12
 */
@EnableConfigurationProperties(CasProperties.class)
@Configurable
@ConditionalOnProperty(prefix = "cas", name = "enable", havingValue = "true", matchIfMissing = true)
public class CasConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public UserLoginService userLoginService() {
        return new SingleSessionUserLoginServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public CasAuthInterceptor casAuthInterceptor(UserLoginService userLoginService, CasProperties casProperties) {
        return new CasAuthInterceptor(userLoginService, casProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public CasReqService casReqService(CasProperties casProperties) {
        return new SimpleCasReqServiceImpl(casProperties);
    }

}
