package com.squareup.otto.sample.test;

import android.content.pm.PackageParser.NewPermissionInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.squareup.otto.Bus;

public class TestOttoFragment extends Fragment implements OnClickListener{

    private static final CommonLog log = LogFactory.createLog();
    
    private static final TestBus mBus = new TestBus();
    
    private Button mButtonEvent1;
    private Button mButtonEvent2;
    private Button mButtonEvent3;
    
    
	  @Override
	    public void onCreate(Bundle state) {
	        super.onCreate(state);

	    }
	    
	    
	    @Override
		public void onDestroy() {


	    	
			super.onDestroy();
		}

		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
	        View view = inflater.inflate(R.layout.test_otto_layout, container, false);

	        mButtonEvent1 = (Button) view.findViewById(R.id.btn_sendevent1);
	        mButtonEvent2 = (Button) view.findViewById(R.id.btn_sendevent2);	       
	        mButtonEvent3 = (Button) view.findViewById(R.id.btn_sendevent3);
		       
	        mButtonEvent1.setOnClickListener(this);
	        mButtonEvent2.setOnClickListener(this);
	        mButtonEvent3.setOnClickListener(this);
	        return view;
	    }

	    @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
	        super.onViewCreated(view, savedInstanceState);

	    }



	    @Override
	    public void onResume() {
	        super.onResume();

	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	        
	        
	    }


		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.btn_sendevent1:
					sendEvent1();
					break;
				case R.id.btn_sendevent2:
					sendEvent2();
					break;
				case R.id.btn_sendevent3:
					sendEvent3();
					break;
			}
		}
	    
		
		
		private void sendEvent1(){
			mBus.post(new CallEvent(0));
		}
		
		private void sendEvent2(){
			mBus.addObjectForCallEvent(new CallSubscribe());
		}
		
		private void sendEvent3(){
			mBus.removeAllSubscribe();
		}
}
