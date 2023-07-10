package top.gink.cas.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceResponse {

    public AuthenticationSuccess authenticationSuccess;
    public AuthenticationFailure authenticationFailure;
}
