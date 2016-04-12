package com.geniusgithub.phonetools;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusgithub.calllog.CallLogQueryHandler;
import com.geniusgithub.phonetools.model.SIMInfo;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class TestConfigureActivity extends Activity implements OnClickListener, CallLogQueryHandler.Listener{

	  private static final CommonLog log = LogFactory.createLog();
	  
	 private Button mButton;
	    private CallLogQueryHandler mCallLogQueryHandler;
	    private TextView mTextView;
	    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_configure_layout);

        log.d("onCreate...");
        
        mButton = (Button) findViewById(R.id.btn_get);
        mButton.setOnClickListener(this);
        
        mTextView = (TextView) findViewById(R.id.textview);
        
        mCallLogQueryHandler = new CallLogQueryHandler(this, getContentResolver(), this);
 
    }
    
    
    
    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    	log.d("onConfigurationChanged newConfig = " + newConfig.toString());
    	  Toast.makeText(this," onConfigurationChanged newConfig ", Toast.LENGTH_SHORT).show();
    }




	
	private void get(){
	    mCallLogQueryHandler.fetchCalls();
	}





	@Override
	public void onClick(View arg0) {
	//	get();
		getInfoByDatabase();
	}





	@Override
	public boolean onCallsFetched(Cursor combinedCursor) {
		  log.i("onCallsFetched cursor = " + combinedCursor);
		  if (isFinishing()) {
	            // Return false; we did not take ownership of the cursor
	            return false;
	        }
		  
		  if (combinedCursor != null){
			  log.i("cursor.getCount = " + combinedCursor.getCount());
			  String value = "通话记录个数:" +  combinedCursor.getCount();
			  mTextView.setText(value);
		  }else{

			  mTextView.setText("未查询到通话记录");
		  }
		  
		
	        return true;
	}
    
	
    public void getInfoByDatabase(){
    	SIMInfo sim0 = SIMInfo.getSIMInfoBySlot(this, 0);
 
    	
    	SIMInfo sim1 = SIMInfo.getSIMInfoBySlot(this, 1);
    	
    	String value = "slotid = 0\n" + (sim0 != null ? sim0.toString() : "null") + 
    					"\n======================\nslotid = 1\n" + (sim1 != null ? sim1.toString() : "null");
    	mTextView.setText(value);
    }

    
}
