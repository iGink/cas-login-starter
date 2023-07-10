package top.gink.cas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import top.gink.cas.interceptor.CasAuthInterceptor;
import top.gink.cas.model.CasProperties;

/**
 * @author Gink
 * @create 2023/7/10 15:24
 */
@Configurable
@AutoConfigureAfter(WebMvcConfigurationSupport.class)
@ConditionalOnProperty(prefix = "cas", name = "enable", value = "true", matchIfMissing = true)
public class CasMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private CasAuthInterceptor casAuthInterceptor;
    @Autowired
    private CasProperties casProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(casAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(casProperties.ignorePath);
        super.addInterceptors(registry);
    }
}
