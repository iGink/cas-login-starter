package top.gink.cas.exception;

/**
 * 常规业务异常，不做系统报警的那种
 */
public class ServiceException extends CustException {

    private static final int ERROR_CODE = 1;

    public ServiceException(String msg) {
        super(ERROR_CODE, msg, null, null);
    }

    public ServiceException(String msg, Exception e) {
        super(ERROR_CODE, msg, null, e);
    }

    public ServiceException(String msg, String detail) {
        super(ERROR_CODE, msg, detail, null);
    }

    public ServiceException(String msg, String detail, Exception e) {
        super(ERROR_CODE, msg, detail, e);
    }

    public ServiceException(CustError en) {
        super(en.code, en.desc, null, null);
    }

    public ServiceException(CustError en, String msg) {
        super(en.code, msg, null, null);
    }

}
