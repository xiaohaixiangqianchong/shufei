package com.ubestkid.kidrhymes;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;


import androidx.multidex.MultiDex;

import com.hisavana.mediation.config.TAdManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.ubestkid.kidrhymes.common.dialog.IDialog;
import com.ubestkid.kidrhymes.common.dialog.NomalDialog;
import com.ubestkid.kidrhymes.common.interceptor.LoggerInterceptor;
import com.ubestkid.kidrhymes.common.ioc.Instance;
import com.ubestkid.kidrhymes.common.ioc.IocContainer;
import com.ubestkid.kidrhymes.constant.Constants;
import com.ubestkid.kidrhymes.presenter.STAdManager;
import com.ubestkid.kidrhymes.utils.LogUtils;
import com.ubestkid.kidrhymes.utils.SharedPreferencesUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class AppApplication extends Application {

    public static AppApplication mContext;
    public String mVersionName;
    public int mVersionCode = 0;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.color_999999);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        getAppVersion();
        IocContainer.getShare().initApplication(this);
        IocContainer.getShare().bind(NomalDialog.class).to(IDialog.class)
                .scope(Instance.InstanceScope.SCOPE_SINGLETON);
        initNet();
        initFileProvider();
        STAdManager.getInstance().initSdk(Constants.APP_ID, this, true,false);
//        TAdManager.init(this, new TAdManager.AdConfigBuilder()
//                .setAppId(Constants.APP_ID)
//                .testDevice(false)
//                .setDebug(true)
//                .build());

//        Bugly.init(getApplicationContext(), "c9458e39dc", false);
    }

    private void initFileProvider() {
        // 解决7.0以上版本的FileProvider问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    public static AppApplication getInstance() {
        return mContext;
    }

    //获取当前版本号和版本名称
    private void getAppVersion() {
        try {
            //getPackageName()是当前类的包名，0代表是获取版本信息
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            //版本名称
            mVersionName = pi.versionName;
            //版本号
            mVersionCode = pi.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtils.d("Application-", "【版本名】:" + mVersionName + "/【版本号】" + mVersionCode);
    }

    private void initNet() {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
//        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(new LoggerInterceptor("person", true));                                 //添加OkGo默认debug日志
//        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
//        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失


        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);                         //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数

        setHttpToken((String) SharedPreferencesUtils.get(mContext, Constants.SharedAPI.TOKEN_KEY, ""));
    }

    public static void setHttpToken(String token) {
        LogUtils.e("setHttpToken-token=", token);
        HttpHeaders headers = OkGo.getInstance().getCommonHeaders();
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put("Access-Token", token);    //header不支持中文，不允许有特殊字符
        OkGo.getInstance().addCommonHeaders(headers);
    }

}
