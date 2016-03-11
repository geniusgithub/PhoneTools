package com.geniusgithub.phonetools;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.geniusgithub.calllog.CallLogFragment;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class APIActivity extends AppCompatActivity {

    private static final CommonLog log = LogFactory.createLog();

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        initView();
    }


    private void initView(){
    	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    	initToolBar(toolbar);
         
    	initDrawLayout(toolbar);

        setupViewPager();
//         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//         fab.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Snackbar.make(view, "Snackbar comes out", Snackbar.LENGTH_LONG)
//                         .setAction("Action", new View.OnClickListener() {
//                             @Override
//                             public void onClick(View v) {
//                                 Toast.makeText(
//                                         APIActivity.this,
//                                         "Toast comes out",
//                                         Toast.LENGTH_SHORT).show();
//                             }
//                         }).show();
//             }
//         });


    }
    
    
    private void initToolBar(Toolbar toolbar){
	  
	     setSupportActionBar(toolbar);
	     toolbar.setTitle("Tools");
	     toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
	    
	     final ActionBar ab = getSupportActionBar();
	     ab.setHomeAsUpIndicator(R.drawable.ic_menu);
	     ab.setDisplayHomeAsUpEnabled(true);


	
    }
    
    private void initDrawLayout(Toolbar toolbar){
    	   mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open,  R.string.close){
               @Override
               public void onDrawerOpened(View drawerView) {
                   super.onDrawerOpened(drawerView);
         
               }
               @Override
               public void onDrawerClosed(View drawerView) {
                   super.onDrawerClosed(drawerView);

               }
           };
     
           
    	   mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
           mDrawerLayout.setDrawerListener(mDrawerToggle);
         
           
           
           NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
           if (navigationView != null) {
               setupDrawerContent(navigationView);
           }
       
        
    }
    
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        
                        
                        
                        switch(menuItem.getItemId()){
	                        case R.id.nav_lite_common:
	                        	goLiteCommon();
	                        	break;
	                        case R.id.nav_lite_http:
	                        	goLiteHttp();
	                        	break;
	                        case R.id.nav_test:
	                        	goTest();
	                        	break;
                        }
                        
                        
                        
                        
                        return true;
                    }
                });
    }
    
    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<String>();
        titles.add("P 1");
        titles.add("P 2");
        titles.add("P 3");
        titles.add("P 4");
        titles.add("P 5");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(4)));
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new SubscriptionInfoFragment());
        fragments.add(new PhoneManagerFragment());
        fragments.add(new UIFragment());
        fragments.add(new CallLogFragment());
        fragments.add(new PermissionFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		log.i("onOptionsItemSelected item.getItemId() = " + item.getItemId() + "\nhome = " + R.id.home + 
					"\nic_menu = " + R.drawable.ic_menu + "\nR.id.homeAsUp = " + R.id.homeAsUp);
		switch(item.getItemId()){
		case R.id.homeAsUp:
			return toggleHomeIcon();
			default:
				return super.onOptionsItemSelected(item);
		}

	}
    
	
	private boolean toggleHomeIcon(){
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
			mDrawerLayout.closeDrawers();
		}else{
			mDrawerLayout.openDrawer(Gravity.LEFT);
		}
		
		return true;
	}
    
    
	
	private void goLiteCommon(){
		Intent intent = new Intent();
		intent.setClass(this, LiteCommonActivity.class);
		startActivity(intent);
	}
    
	
	private void goLiteHttp(){
		Intent intent = new Intent();
		intent.setClass(this, LiteHttpActivity.class);
		startActivity(intent);
	}
	
	
	private void goTest(){
		Intent intent = new Intent();
		intent.setClass(this, TestActivity.class);
		startActivity(intent);
	}
}
