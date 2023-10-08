package top.gink.cas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.gink.cas.interceptor.CasAuthInterceptor;
import top.gink.cas.model.CasProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gink
 * @create 2023/7/10 15:24
 */
@Configurable
@AutoConfigureAfter(WebMvcConfigurationSupport.class)
@ConditionalOnProperty(prefix = "cas", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CasMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private CasAuthInterceptor casAuthInterceptor;
    @Autowired
    private CasProperties casProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> ignorePath = getIgnorePath();
        registry.addInterceptor(casAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ignorePath);
    }

    private List<String> getIgnorePath() {
        if (CollectionUtils.isEmpty(casProperties.ignorePath)) {
            return new ArrayList<>();
        }
        return casProperties.ignorePath;
    }
}
