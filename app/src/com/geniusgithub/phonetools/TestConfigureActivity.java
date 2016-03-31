package com.geniusgithub.phonetools;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

import android.app.Activity;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TestConfigureActivity extends Activity{

	  private static final CommonLog log = LogFactory.createLog();
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_configure_layout);

        log.d("onCreate...");
        
        Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
    }
    
    
    
    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    	log.d("onConfigurationChanged newConfig = " + newConfig.toString());
    	  Toast.makeText(this," onConfigurationChanged newConfig ", Toast.LENGTH_SHORT).show();
    }
    
    
}
