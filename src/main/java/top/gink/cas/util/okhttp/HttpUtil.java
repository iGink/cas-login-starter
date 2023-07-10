package top.gink.cas.util.okhttp;

import com.ejlchina.okhttps.*;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import top.gink.cas.util.gson.GsonConfig;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * OkHttps 工具类,放弃SPI的配置方式，SPI打包后不可用问题无法解决
 */
public final class HttpUtil {

    // 使用 x-www-form-urlencoded 表单格式
    public static final String FORM = "form";
    // 使用 form-data 表单格式（一般上传文件时使用）
    public static final String FORM_DATA = "form-data";
    public static final String JSON = "json";
    public static final String XML = "xml";
    public static final String PROTOBUF = "protobuf";
    public static final String MSGPACK = "msgpack";

    private static HTTP http;

    private HttpUtil() {
    }

    public static HTTP getHttp() {
        return getHttp(false);
    }

    /**
     * @param trustHttps 信任所有https证书
     * @return
     */
    public static synchronized HTTP getHttp(boolean trustHttps) {
        if (http != null) {
            return http;
        }
        HTTP.Builder builder = HTTP.builder();
        // 在这里对 HTTP.Builder 做一些自定义的配置
        Gson gson = GsonConfig.getGsonBuilder().create();
        builder.addMsgConvertor(new GsonMsgConvertor(gson));

        builder.config(c -> {
            c.connectTimeout(30, TimeUnit.SECONDS);
            c.readTimeout(30, TimeUnit.SECONDS);
            c.addInterceptor(new HttpLoggingInterceptor());
            if (trustHttps) {
                SSLSocketFactory mySSLSocketFactory = getSslSocketFactory();
                X509TrustManager myTrustManager = getX509TrustManager();
                HostnameVerifier myHostnameVerifier = getHostnameVerifier();
                c.sslSocketFactory(mySSLSocketFactory, myTrustManager);
                c.hostnameVerifier(myHostnameVerifier);
            }
        });
        http = builder.build();
        return http;
    }

    @SneakyThrows
    private static SSLSocketFactory getSslSocketFactory() {
        SSLContext sslCtx = SSLContext.getInstance("TLS");
        X509TrustManager myTrustManager = getX509TrustManager();
        sslCtx.init(null, new TrustManager[]{myTrustManager}, new SecureRandom());
        return sslCtx.getSocketFactory();
    }

    private static X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private static HostnameVerifier getHostnameVerifier() {
        return (hostname, session) -> true;
    }


    /**
     * 异步请求
     *
     * @param url 请求地址
     * @return 异步 HttpTask
     */
    public static AHttpTask async(String url) {
        return getHttp().async(url);
    }

    /**
     * 同步请求
     *
     * @param url 请求地址
     * @return 同步 HttpTask
     */
    public static SHttpTask sync(String url) {
        return getHttp().sync(url);
    }

    /**
     * Websocket 连接
     *
     * @param url 连接地址
     * @return WebSocket 任务
     */
    public static WHttpTask webSocket(String url) {
        return getHttp().webSocket(url);
    }

    /**
     * 根据标签取消HTTP任务，只要任务的标签包含指定的Tag就会被取消
     *
     * @param tag 标签
     * @return 被取消的任务数量
     */
    public static int cancel(String tag) {
        return getHttp().cancel(tag);
    }

    /**
     * OkHttp 原生请求 （该请求不经过 预处理器）
     *
     * @param request 请求
     * @return Call
     */
    public static Call request(Request request) {
        return getHttp().request(request);
    }

    /**
     * Websocket（该请求不经过 预处理器）
     *
     * @param request  请求
     * @param listener 监听器
     * @return WebSocket
     */
    public static WebSocket webSocket(Request request, WebSocketListener listener) {
        return getHttp().webSocket(request, listener);
    }

    /**
     * @return Builder
     * @since 2.2.0 新的构建器
     */
    public static HTTP.Builder newBuilder() {
        return getHttp().newBuilder();
    }

    /**
     * 获取任务执行器
     *
     * @return TaskExecutor
     */
    public static TaskExecutor getExecutor() {
        return getHttp().executor();
    }

    /**
     * @since 1.0.3 取消所有HTTP任务，包括同步和异步
     */
    public void cancelAll() {
        getHttp().cancelAll();
    }
}
