package com.geniusgithub.phonetools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.geniusgithub.phonetools.util.PermissionsUtil;

public class PermissionFragment extends Fragment implements OnClickListener{

	 private static final CommonLog log = LogFactory.createLog();
	 private Context mContext;
	 private Button mButtonPermission;
	 
	 
	 
	 private final int REQUEST_PHONE_PERMISSION =  0X0001;
	 private final int REQUEST_LOCATION_PERMISSION =  0X0002;
	 private final int REQUEST_STORAGE_PERMISSION =  0X0003;
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =  LayoutInflater.from(mContext).inflate(R.layout.permission_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonPermission = (Button) view.findViewById(R.id.btn_permission);
        mButtonPermission.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_permission:
			getPermission();	
			break;
		}
	}
	
	private void getPermission(){
		boolean start = RequestPermissionsActivity.startPermissionActivity(getActivity());
		if (!start){
			Toast.makeText(mContext, "hasNecessaryRequiredPermissions!!!", Toast.LENGTH_SHORT).show();
		}
//		boolean flag = PermissionsUtil.hasNecessaryRequiredPermissions(mContext);
//		log.i("hasNecessaryRequiredPermissions = " + flag);
//		if (flag){
//			Toast.makeText(mContext, "hasNecessaryRequiredPermissions!!!", Toast.LENGTH_SHORT).show();
//		}else{
//		
//			
//			requestNecessaryRequiredPermissions();
//		}
	}
	
	private void requestNecessaryRequiredPermissions(){
		
		
		String []permission = new String[]{PermissionsUtil.PHONE,
											PermissionsUtil.LOCATION,
											PermissionsUtil.STORAGE};
		
		
		
		requestSpecialPermissions(PermissionsUtil.PHONE, REQUEST_PHONE_PERMISSION);
		
	}

	
	private void requestSpecialPermissions(String permission, int requestCode){
		String []permissions = new String[]{permission};
		requestPermissions(permissions, requestCode);
	}



	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		
		switch(requestCode){
		case REQUEST_PHONE_PERMISSION:
			doPhonePermission(grantResults);
			break;
		case REQUEST_LOCATION_PERMISSION:
			doLocationPermission(grantResults);
			break;
		case REQUEST_STORAGE_PERMISSION:
			doStoragePermission(grantResults);
			break;
				
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
				break;
		}


	}
	
	
	private void doPhonePermission(int[] grantResults){
		if (grantResults[0] == PackageManager.PERMISSION_DENIED){
			log.e("doPhonePermission is denied!!!" );
			Dialog dialog = PermissionsUtil.createPermissionSettingDialog(getActivity(), "电话权限");
			dialog.show();
		}else if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
			log.i("doPhonePermission, is granted!!!" );
			requestSpecialPermissions(PermissionsUtil.LOCATION, REQUEST_LOCATION_PERMISSION);
		}
	
	}
	
	
	private void doLocationPermission(int[] grantResults){
		if (grantResults[0] == PackageManager.PERMISSION_DENIED){
			log.e("doLocationPermission is denied!!!" );
			Dialog dialog = PermissionsUtil.createPermissionSettingDialog(getActivity(), "位置权限");
			dialog.show();
		}else if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
			log.i("doLocationPermission, is granted!!!" );
			requestSpecialPermissions(PermissionsUtil.STORAGE, REQUEST_STORAGE_PERMISSION);
		}
	
	}
	
	
	private void doStoragePermission(int[] grantResults){
		if (grantResults[0] == PackageManager.PERMISSION_DENIED){
			log.e("doStoragePermission is denied!!!" );
			Dialog dialog = PermissionsUtil.createPermissionSettingDialog(getActivity(), "存储权限");
			dialog.show();
		}else if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
			log.i("doStoragePermission, is granted!!!" );
			Toast.makeText(mContext, "doStoragePermission success!!!", Toast.LENGTH_SHORT).show();
		}
	
	}
	
}
