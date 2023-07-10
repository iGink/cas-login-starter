package top.gink.cas.exception;


/**
 * @author Gink
 * @create 2019/10/18 12:44 下午
 */
public enum CustError {
    ERROR(1, "服务异常"),
    NEED_LOGIN(100_000_001, "需要登录"),

    ;
    public int code;
    public String desc;

    CustError(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CustError byCode(int code) {
        for (CustError val : values()) {
            if (val.code == code) {
                return val;
            }
        }
        throw new RuntimeException("未知的code");
    }
}
