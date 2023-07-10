package top.gink.cas.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "cas")
public class CasProperties {
    public boolean enabled;
    public String loginSuccessUrl;

    public String baseUrl;
    public String serviceValidateUrl;
    public String loginUrl;
    public String logoutUrl;
    public String callbackUrl;
    public List<String> ignorePath;

}
