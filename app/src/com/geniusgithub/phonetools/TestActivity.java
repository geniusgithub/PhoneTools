package com.geniusgithub.phonetools;

import java.util.concurrent.FutureTask;

import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;
import com.litesuits.http.utils.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends AppCompatActivity implements OnClickListener{

	private Button mBtnTest;
	
    protected static LiteHttp liteHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        initView();
        initLiteHttp();
    }

    
    private void initView(){
   	 Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      final ActionBar ab = getSupportActionBar();
      ab.setHomeAsUpIndicator(R.drawable.ic_menu);
      ab.setDisplayHomeAsUpEnabled(true);
      
      mBtnTest = (Button) findViewById(R.id.btn_test);
      mBtnTest.setOnClickListener(this);

    }
    
    /**
     * 单例 keep an singleton instance of litehttp
     */
    private void initLiteHttp() {
        if (liteHttp == null) {
            HttpConfig config = new HttpConfig(this) // configuration quickly
                    .setDebugged(true)                   // log output when debugged
                    .setDetectNetwork(true)              // detect network before connect
                    .setDoStatistics(true)               // statistics of time and traffic
                    .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
                    .setTimeOut(10000, 10000);             // connect and socket timeout: 10s
            liteHttp = LiteHttp.newApacheHttpClient(config);
        } else {
            liteHttp.getConfig()                        // configuration directly
                    .setDebugged(true)                  // log output when debugged
                    .setDetectNetwork(true)             // detect network before connect
                    .setDoStatistics(true)              // statistics of time and traffic
                    .setUserAgent("Mozilla/5.0 (...)")  // set custom User-Agent
                    .setTimeOut(10000, 10000);            // connect and socket timeout: 10s
        }
    }


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_test:
			test();
			break;

		default:
			break;
		}
	}
	
	
    public static final String url = "http://baidu.com";
	private void test(){
		  final StringRequest request = new StringRequest(url).setHttpListener(
                  new HttpListener<String>() {
                      @Override
                      public void onSuccess(String s, Response<String> response) {
                          HttpUtil.showTips(TestActivity.this, "LiteHttp2.0", s);
                          response.printInfo();
                      }

                      @Override
                      public void onFailure(HttpException e, Response<String> response) {
                          HttpUtil.showTips(TestActivity.this, "LiteHttp2.0", e.toString());
                      }
                  }
          );

          // 1.1 execute async, nothing returned.
          liteHttp.executeAsync(request);

          // 1.2 perform async, future task returned.
          FutureTask<String> task = liteHttp.performAsync(request);
          task.cancel(true);
		
	}
}
