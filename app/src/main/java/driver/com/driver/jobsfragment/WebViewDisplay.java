package driver.com.driver.jobsfragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import driver.com.driver.R;
import driver.com.driver.constants.IConstant;

/**
 * Created by androidusr1 on 17/8/16.
 */
public class WebViewDisplay extends Activity {
    private WebView webView;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        webView = (WebView) findViewById(R.id.webView1);
        close=(ImageView)findViewById(R.id.image_webclose);
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        try {
            String img1 = getIntent().getExtras().getString("img1");
            String img2 = getIntent().getExtras().getString("img2");
            String img3 = getIntent().getExtras().getString("img3");
            String img4 = getIntent().getExtras().getString("img4");
            String img5 = getIntent().getExtras().getString("img5");
            String image = getIntent().getExtras().getString("add_image");


            if (img1 != null) {
                webView.loadUrl(IConstant.uri + img1);
            }
            if (img2 != null) {
                webView.loadUrl(IConstant.uri + img2);
            }
            if (img3 != null) {
                webView.loadUrl(IConstant.uri + img3);
            }
            if (img4 != null) {
                webView.loadUrl(IConstant.uri + img4);
            }
            if (img5 != null) {
                webView.loadUrl(IConstant.uri + img5);
            }
            if (image != null) {
                webView.loadUrl(IConstant.uri + image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }
}
