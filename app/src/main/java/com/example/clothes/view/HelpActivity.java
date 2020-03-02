package com.example.clothes.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.clothes.view.ActivityList.ActivityList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.clothes.R;

public class HelpActivity extends AppCompatActivity {

    private WebView mWebview;
    private String URL = "https://rafaelamomoral.github.io/Android/";
    public static final String LIST_PAGE = "list/list.html";
    public static final String FORM_PAGE = "form/form.html";
    public static final String SEARCH_PAGE = "search/search.html";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        setWebView(getIntent().getExtras().getString("PAGE"));

    }

    private void setWebView(String page) {

        if (checkPage(page)) {
            final Activity activity = this;

            if (isOnline()) {
                mWebview = findViewById(R.id.help_webview);

                mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript


                mWebview.setWebViewClient(new WebViewClient() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
                    }

                    @TargetApi(android.os.Build.VERSION_CODES.M)
                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                        // Redirect to deprecated method, so you can use it in all SDK versions
                        Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                        onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

                mWebview.loadUrl(URL + page);

            } else {
                Toast.makeText(activity, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isOnline() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
                connected = true;
            }
        }
        return connected;
    }

    public boolean checkPage(String page){
        boolean valid=false;
        if(page!=null){
            switch (page){
                case LIST_PAGE:
                    valid=true;
                    break;
                case FORM_PAGE:
                    valid=true;
                    break;
                case SEARCH_PAGE:
                    valid=true;
                    break;
            }
        }
        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
