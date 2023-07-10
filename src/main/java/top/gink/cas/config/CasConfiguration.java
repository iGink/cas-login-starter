package top.gink.cas.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Gink
 * @create 2023/7/10 14:12
 */
@EnableConfigurationProperties(CasProperties.class)
@Configurable
public class CasConfiguration {
}
