package com.ubestkid.kidrhymes.common.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "net";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = TAG;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);

        return logForResponse(response);
    }

    private void logForRequest(Request request) {
        // try {
        // String url = request.url().toString();
        // Headers headers = request.headers();
        //
        // Log.d(tag,
        // "---------------------request log start---------------------");
        //
        // // Log.d(tag, "url : " + url);
        // // if (headers != null && headers.size() > 0) {
        // // Log.d(tag, "headers : \n");
        // // Log.d(tag, headers.toString());
        // // }
        // RequestBody requestBody = request.body();
        // if (requestBody != null) {
        // MediaType mediaType = requestBody.contentType();
        // if (mediaType != null) {
        // Log.d(tag, "contentType : " + mediaType.toString());
        // if (isText(mediaType)) {
        // Log.d(tag, "content : " + bodyToString(request));
        // } else {
        // Log.d(tag, "content : " +
        // " maybe [file part] , too large too print , ignored!");
        // }
        // }
        // }
        // } catch (Exception e) {
        // OkLogger.e(e);
        // } finally {
        // // Log.d(tag,
        // "---------------------request log end-----------------------");
        // }
    }

    private Response logForResponse(Response response) {
        try {
            Log.d(tag,
                    "---------------------request log start---------------------");

            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.d(tag, "url : " + clone.request().url());
            Log.d(tag, "method : " + clone.request().method());
            RequestBody requestBody = clone.request().body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    // Log.d(tag, "contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.d(tag, "params : " + bodyToString(clone.request()));
                    } else {
                        Log.d(tag,
                                "params : "
                                        + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.d(tag, "code : " + clone.code());

            // Log.d(tag, "protocol : " + clone.protocol());
            // if (!TextUtils.isEmpty(clone.message()))
            // Log.d(tag, "message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    String resp = body.string();
                    Log.d(tag, "result : " + resp);
//                    logd(tag, "result : " + resp); //会出现无限循环 待优化
                    body = ResponseBody.create(mediaType, resp);
                    return response.newBuilder().body(body).build();
//                    if (mediaType != null) {
//                        // Log.e(tag, "contentType : " + mediaType.toString());
//                        if (isText(mediaType)) {
////                            String resp = body.string();
////                            Log.d(tag, "result : " + resp);
//                            body = ResponseBody.create(mediaType, resp);
//                            return response.newBuilder().body(body).build();
//                        } else {
//                            Log.d(tag,
//                                    "content : "
//                                            + " maybe [file part] , too large too print , ignored!");
//                        }
//                    }
                }
            }
        } catch (Exception e) {
            if (e != null && e.getMessage() != null){
                Log.e("tag", e.getMessage());
            }
        } finally {
            Log.d(tag,
                    "---------------------response log end-----------------------");
        }

        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.toString()
                    .equals("application/x-www-form-urlencoded") ||
                    mediaType.toString()
                            .equals("application/json")
                    || mediaType.subtype().equals("json")
                    || mediaType.subtype().equals("xml")
                    || mediaType.subtype().equals("html")
                    || mediaType.subtype().equals("webviewhtml")) //
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void logd(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.d(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.d(tag, logContent);
            }
            Log.d(tag, msg);// 打印剩余日志
        }
    }
}
