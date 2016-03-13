package com.geniusgithub.phonetools.productcode;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.geniusgithub.phonetools.FragmentAdapter;
import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;

public class ProductCodeActivity extends AppCompatActivity {

    private static final CommonLog log = LogFactory.createLog();

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    
	private static EditText mEditText;
	

    protected static LiteHttp liteHttp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productcode_layout);

        initView();
    }

    public static String getCodeFromEditText(){
    	return mEditText.getText().toString();
    }

    private void initView(){
    	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    	initToolBar(toolbar);

        mEditText = (EditText) findViewById(R.id.et_code);
        //    mEditText.setText("6945627000276");
        mEditText.setText("6922266445057");
        
        setupViewPager();

    }
    

    
    private void initToolBar(Toolbar toolbar){
	  
	     setSupportActionBar(toolbar);
	     toolbar.setTitle("Tools");
	     toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
	    
	     final ActionBar ab = getSupportActionBar();
	     ab.setHomeAsUpIndicator(R.drawable.ic_menu);
	     ab.setDisplayHomeAsUpEnabled(true);

    }
    
    
    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<String>();
        titles.add("条码追溯");
        titles.add("条码达人");
        titles.add("联图");
        titles.add("物品编码");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
        
        List<Fragment> fragments = new ArrayList<Fragment>();
        ProductCodeFragment fragment1 = new ProductCodeFragment();
        fragment1.setType(ProductCodeFragment.TMZS_TYPE);
        fragments.add(fragment1);
        ProductCodeFragment fragment2 = new ProductCodeFragment();
        fragment2.setType(ProductCodeFragment.TMDR_TYPE);
        fragments.add(fragment2);
        ProductCodeFragment fragment3 = new ProductCodeFragment();
        fragment3.setType(ProductCodeFragment.LT_TYPE);
        fragments.add(fragment3);
        ProductCodeFragment fragment4 = new ProductCodeFragment();
        fragment4.setType(ProductCodeFragment.WPBM_TYPE);
        fragments.add(fragment4);
      
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        
        mViewPager.setOffscreenPageLimit(fragments.size());

    }
    
    public static LiteHttp getLiteHttp(Context context) {
        if (liteHttp == null) {
            HttpConfig config = new HttpConfig(context.getApplicationContext()) // configuration quickly
                    .setDebugged(true)                   // log output when debugged
                    .setDetectNetwork(true)              // detect network before connect
                    .setDoStatistics(true)               // statistics of time and traffic
                    .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
                    .setTimeOut(10000, 10000);             // connect and socket timeout: 10s
            liteHttp = LiteHttp.newApacheHttpClient(config);
        } 
        return liteHttp;
    }
    


}
