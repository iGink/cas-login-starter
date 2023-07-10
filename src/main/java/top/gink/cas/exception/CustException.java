package top.gink.cas.exception;

/**
 * @author Gink
 * @create 2021-12-02 18:59:39
 */
public class CustException extends RuntimeException {

    public int code;
    public String msg;
    public String detail;

    public CustException(String msg, String detail) {
        this(1, msg, detail, null);
    }

    public CustException(String msg, Exception e) {
        this(1, msg, null, e);
    }

    public CustException(int code, String msg, String detail) {
        this(code, msg, detail, null);
    }

    public CustException(int code, String msg, String detail, Exception e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }
}
