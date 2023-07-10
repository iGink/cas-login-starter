package top.gink.cas.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationFailure {

    public String code;
    public String description;
}
