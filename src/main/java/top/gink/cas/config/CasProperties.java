package top.gink.cas.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "cas")
public class CasProperties {
    private boolean enabled = true;
}
