package top.gink.cas.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.gink.cas.interceptor.CasAuthInterceptor;
import top.gink.cas.service.UserLoginService;
import top.gink.cas.service.impl.SingleSessionUserLoginServiceImpl;

/**
 * @author Gink
 * @create 2023/7/10 14:12
 */
@EnableConfigurationProperties(CasProperties.class)
@Configurable
public class CasConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public UserLoginService userLoginService() {
        return new SingleSessionUserLoginServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public CasAuthInterceptor casAuthInterceptor(UserLoginService userLoginService) {
        return new CasAuthInterceptor(userLoginService);
    }

}
