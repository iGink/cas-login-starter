/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.gink.cas.util.okhttp;

import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import top.gink.cas.log.WebLog;
import top.gink.cas.util.gson.GsonConfig;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a
 * {@linkplain OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs
 * created by this class should not be considered stable and may change slightly between releases.
 * If you need a stable logging format, use your own interceptor.
 */
public final class HttpLoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private final Gson gson = GsonConfig.getGsonBuilder()
            .disableHtmlEscaping()
            .create();

    public HttpLoggingInterceptor() {

    }


    /**
     * Returns true if the body in question probably contains human readable text. Uses a small
     * sample of code points to detect unicode control characters commonly used in binary file
     * signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Connection connection = chain.connection();
        String uuid = UUID.randomUUID().toString();

        Map<String, Object> reqMap = getReqMap(request, connection, uuid);

        WebLog.httpLog.info("req: " + gson.toJson(reqMap));

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            WebLog.errorLog.error(gson.toJson(reqMap), e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = -1;
        if (responseBody != null) {
            contentLength = responseBody.contentLength();
        }
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        Headers headers = response.headers();

        String respBody = null;
        Long gzippedLength = null;
        if (!HttpHeaders.hasBody(response)) {
            respBody = "";
        } else if (bodyHasUnknownEncoding(headers)) {
            respBody = "encoded body omitted";
        } else if (responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.getBuffer();

            if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                gzippedLength = buffer.size();
                try (GzipSource gzippedResponseBody = new GzipSource(buffer.clone())) {
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                }
            }

            if (isPlaintext(buffer) && contentLength != 0) {
                respBody = buffer.clone().readString(UTF8);
            }
        }

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("contentLength", contentLength);
        respMap.put("bodySize", bodySize);
        respMap.put("code", response.code());
        respMap.put("message", response.message());
        respMap.put("tookMs", tookMs);
        respMap.put("headers", headers.toMultimap());
        respMap.put("respBody", respBody);
        respMap.put("gzippedLength", gzippedLength);
        respMap.put("uuid", uuid);

        WebLog.httpLog.info("resp: " + gson.toJson(respMap));

        return response;
    }

    private Map<String, Object> getReqMap(Request request, Connection connection, String uuid)
            throws IOException {
        RequestBody requestBody = request.body();
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("url", request.url());
        reqMap.put("method", request.method());
        reqMap.put("headers", request.headers().toMultimap());
        if (connection != null) {
            reqMap.put("protocol", connection.protocol());
        }
        if (requestBody != null) {
            reqMap.put("contentLength", requestBody.contentLength());
            reqMap.put("contentType", requestBody.contentType());
        }
        String reqBody = getReqBody(request);
        reqMap.put("uuid", uuid);
        reqMap.put("reqBody", reqBody);
        return reqMap;
    }

    private String getReqBody(Request request) throws IOException {
        RequestBody requestBody = request.body();

        if (bodyHasUnknownEncoding(request.headers())) {
            return "encoded body omitted";
        }
        if (requestBody == null) {
            return "reqBody is null ";
        }
        if (requestBody.isDuplex()) {
            return "duplex request body omitted";
        }
        if (requestBody.isOneShot()) {
            return "one shot request body omitted";
        }
        MediaType mediaType = requestBody.contentType();
        if (mediaType != null
                && Objects.equals(mediaType.subtype(), "form-data")) {
            return "form-data not print";
        }
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);

        if (!isPlaintext(buffer)) {
            return String.format(" (binary %d-byte body omitted)", requestBody.contentLength());
        }
        String reqBody = buffer.readString(UTF8);
        return reqBody;
    }
}
