package com.mikaco.foodbama.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.mikaco.foodbama.R;


public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        String url = getIntent().getStringExtra("Url");
        WebView webView = (WebView) findViewById(R.id.myweb);
        final TextView lblTitle = findViewById(R.id.txtPageTitle);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                lblTitle.setText(view.getTitle());
                lblTitle.setSelected(true);
            }
        });
        webView.loadUrl(url);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
