package com.atguigu.androidandh5;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/28 11:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：java和js互调
 */
public class JsCallJavaCallPhoneActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);

        //支持双击-前提是页面要支持才显示
//        webSettings.setUseWideViewPort(true);

        //支持缩放按钮-前提是页面要支持才显示
//        webSettings.setBuiltInZoomControls(true);

        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());

        //设置支持js调用java
        webView.addJavascriptInterface(new AndroidAndJSInterface(), "Android");

        //加载本地资源
//        webView.loadUrl("http://atguigu.com/teacher.shtml");
        webView.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");
//        webView.loadUrl("http://10.0.2.2:8080/assets/JsCallJavaCallPhone.html");
//        webView.loadUrl("http://172.25.26.77:8080/JsCallJavaCallPhone.html");

    }

    class AndroidAndJSInterface {
        /**
         * 该方法将被js调用,用于加载数据
         */
        @JavascriptInterface
        public void showcontacts() {
            // 下面的代码建议在子线程中调用
//            Log.d("Main", "调用了");
//            String json = "[{\"name\":\"阮晓勇\", \"phone\":\"15735291098\"}]";
            //这个代码无法调用，因为loadUrl没有加载完成，解决这个问题。可以1）从界面按钮调用 2)延时调用。 3）在onPageFinished中调用
//            webView.setWebViewClient(new MyWebViewClient());
//            // 调用JS中的方法
//            webView.loadUrl("javascript:show('" + json + "')");
        }

        /**
         * 拨打电话
         *
         * @param phone
         */
        @JavascriptInterface
        public void call(String phone) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(JsCallJavaCallPhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);

        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView webView, String url) {
//            Log.d("Main", "调用了");
            String json = "[{\"name\":\"阮晓勇\", \"phone\":\"15735291098\"}]";
            webView.loadUrl("javascript:show('" + json + "')");
//            Log.d("Main", "之后调用了");
        }
    }
}
