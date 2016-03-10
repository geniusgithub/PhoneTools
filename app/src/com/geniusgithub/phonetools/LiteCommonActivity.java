package com.geniusgithub.phonetools;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class LiteCommonActivity extends AppCompatActivity{

	  private static final CommonLog log = LogFactory.createLog();

	  private Toolbar mToolbar;
	  
	  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lite_common_layout);

        initView();
    }
	  
    private void initView(){
    	mToolbar = (Toolbar) findViewById(R.id.toolbar2);
    	initToolBar(mToolbar);
    }

    
    private void initToolBar(Toolbar toolbar){
	     setSupportActionBar(toolbar);
	     toolbar.setTitle("lite");
	     toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

	
    }
	    
}
