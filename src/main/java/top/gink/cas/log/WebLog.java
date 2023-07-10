package top.gink.cas.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gink
 * @create 2021/7/18 11:10 上午
 */
public class WebLog {

    public static Logger reqLog = LoggerFactory.getLogger("reqLogger");
    public static Logger httpLog = LoggerFactory.getLogger("httpLogger");
    public static Logger respLog = LoggerFactory.getLogger("rspLogger");
    public static Logger messLog = LoggerFactory.getLogger("messLogger");
    public static Logger infoLog = LoggerFactory.getLogger("infoLogger");
    public static Logger errorLog = LoggerFactory.getLogger("errorLogger");

    public static Logger tmpLog = LoggerFactory.getLogger("tmpLogger");

    private WebLog() {
    }
}
