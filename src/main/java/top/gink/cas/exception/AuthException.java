package top.gink.cas.exception;

/**
 * 常规业务异常，不做系统报警的那种
 */
public class AuthException extends CustException {

    private static final int ERROR_CODE = CustError.ERROR.code;

    public AuthException(String msg) {
        super(ERROR_CODE, msg, null, null);
    }

    public AuthException(String msg, Exception e) {
        super(ERROR_CODE, msg, null, e);
    }

    public AuthException(String msg, String detail) {
        super(ERROR_CODE, msg, detail, null);
    }

    public AuthException(String msg, String detail, Exception e) {
        super(ERROR_CODE, msg, detail, e);
    }

    public AuthException(CustError en) {
        super(en.code, en.desc, null, null);
    }

    public AuthException(CustError en, String msg) {
        super(en.code, msg, null, null);
    }

}
