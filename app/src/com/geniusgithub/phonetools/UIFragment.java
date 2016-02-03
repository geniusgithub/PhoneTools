package com.geniusgithub.phonetools;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UIFragment extends Fragment implements View.OnClickListener{

	 private static final CommonLog log = LogFactory.createLog();
	 private Context mContext;
	 private View mRootView;
	 private Button mBtnShadow;
	    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.ui_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnShadow = (Button) mRootView.findViewById(R.id.btn_shadow1);
        Drawable drawable = mContext.getDrawable(R.drawable.call_bar_dial2_subordinate);
        int width = drawable.getMinimumWidth();
        int height = drawable.getMinimumHeight();
        log.i( "w = " + width + ", h = " + height);
        drawable.setBounds(0, 0, width, height);
        mBtnShadow.setCompoundDrawables(drawable, null, null, null);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
