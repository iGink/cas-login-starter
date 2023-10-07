package top.gink.cas.model;

public class ServiceResponse {

    public AuthenticationSuccess authenticationSuccess;
    public AuthenticationFailure authenticationFailure;

    public AuthenticationSuccess getAuthenticationSuccess() {
        return authenticationSuccess;
    }

    public void setAuthenticationSuccess(AuthenticationSuccess authenticationSuccess) {
        this.authenticationSuccess = authenticationSuccess;
    }

    public AuthenticationFailure getAuthenticationFailure() {
        return authenticationFailure;
    }

    public void setAuthenticationFailure(AuthenticationFailure authenticationFailure) {
        this.authenticationFailure = authenticationFailure;
    }
}
