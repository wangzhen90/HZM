package com.hengze.hengzemanager.net;

import android.util.Log;
import com.squareup.okhttp.OkHttpClient;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

/**
 * Created by Administrator on 2016/1/10.
 */
public class ApiClient {
  public static final int CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s
  public static final int READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
  public static final String TAG = ApiClient.class.getSimpleName();
  public Api api;
  private ApiClient() {

    // Create a very simple REST adapter which points the API endpoint.
    RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(AppConfig.BASE_URL)//设置host
        .setClient(getOkClient());    //设置client
        //.setRequestInterceptor(new ApiHeaders()).setErrorHandler(new ApiErrorHandler());//设置拦截器
    if (AppConfig.LOG_LEVEL == Log.DEBUG) {//打印日志
      builder.setLog(new AndroidLog(TAG)).setLogLevel(RestAdapter.LogLevel.FULL);
    }

    RestAdapter restAdapter = builder.build();

    // Create an instance of our API interface.
    api = restAdapter.create(Api.class);
  }

  public static class Builder {
    /**
     * Create the {@link ApiClient} instances.
     */
    public ApiClient build() {

      return new ApiClient();
    }
  }

  public static ApiClient get() {
    return new Builder().build();
  }


  public static OkClient getOkClient() {
    OkHttpClient okHttpClient = getUnsafeOkHttpClient();
    if (okHttpClient != null) {
      return new OkClient(okHttpClient);
    } else {
      return new OkClient();
    }
  }

  public static OkHttpClient getUnsafeOkHttpClient() {

    try {
      // Create a trust manager that does not validate certificate chains
      final TrustManager[] trustAllCerts = new TrustManager[] {
          new X509TrustManager() {

            @Override public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {

            }

            @Override public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {

            }

            @Override public X509Certificate[] getAcceptedIssuers() {
              return null;
            }
          }
      };

      // Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      // Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      OkHttpClient okHttpClient = new OkHttpClient();
      okHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
      okHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
      okHttpClient.setSslSocketFactory(sslSocketFactory);
      okHttpClient.setHostnameVerifier(new HostnameVerifier() {
        @Override public boolean verify(String hostname, SSLSession session) {
          //Timber.d("verify hostname: " + hostname);
          return true;
          //if (AppConfig.API_URL.equals(hostname)) {
          //  return true;
          //} else {
          //  return false;
          //}
        }
      });
      return okHttpClient;
    } catch (Exception e) {
      e.printStackTrace();
      //Timber.e("getUnsafeOkHttpClient error!");
      return null;
    }
  }

}
