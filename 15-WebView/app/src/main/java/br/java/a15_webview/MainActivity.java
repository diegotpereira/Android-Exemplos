package br.java.a15_webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "dominando");
        webView.loadUrl("file:///android_asset/meu.html");
    }

    @JavascriptInterface
    public void showToast(String s, String t) {
        Toast.makeText(this, "Nome: " + s + "Idade: " + t, Toast.LENGTH_SHORT).show();
    }
}