package com.example.student.newsapp.Common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.newsapp.R;

import java.util.HashMap;


public class WebviewActivity extends Activity implements ConstVariable{

    TextView tv_text;
    Typeface tf_MTCORSVA;
    WebView webView;
    String str_web;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
        listener();
    }

    private void init(){

        webView = (WebView)findViewById(R.id.webview);


        str_web =getIntent().getStringExtra(INTENT_CHANNELURL);


        Intent intent_dialog = new Intent(getApplicationContext(), DialogActivity.class);
        startActivity(intent_dialog);
        webView.loadUrl(str_web);
        Log.e("Url",str_web);


//        webView.loadUrl(str_web);
        webView.requestFocus();

    }

    private void listener() {


        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                try {
                    DialogActivity.activity.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }


    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }
}
/*

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.student.newsapp.R;

import java.util.HashMap;


public class WebviewActivity extends Activity implements  ConstVariable{

    WebView webView;
    String str_web;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Log.d("webview","created");

        init();
        listener();

    }

    private void init(){

        webView = (WebView)findViewById(R.id.webview);
        str_web =getIntent().getStringExtra(INTENT_CHANNELURL);
        webView.loadUrl(str_web);
        Log.e("Url",str_web);

        Intent intent_dialog = new Intent(getApplicationContext(), DialogActivity.class);
        startActivity(intent_dialog);

//        webView.loadUrl(str_web);
        webView.requestFocus();

    }

    private void listener() {
        Log.d("webview","listener");
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                try {
              DialogActivity.activity.finish();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }
}
*/
