package com.geniusgithub.phonetools;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.geniusgithub.calllog.CallLogFragment;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class APIActivity extends AppCompatActivity {

    private static final CommonLog log = LogFactory.createLog();

    
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_layout);

        initView();
    }


    private void initView(){
    	 Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         final ActionBar ab = getSupportActionBar();
         ab.setHomeAsUpIndicator(R.drawable.ic_menu);
         ab.setDisplayHomeAsUpEnabled(true);

         mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
//         NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
//         if (navigationView != null) {
//             setupDrawerContent(navigationView);
//         }

//         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//         fab.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Snackbar.make(view, "Snackbar comes out", Snackbar.LENGTH_LONG)
//                         .setAction("Action", new View.OnClickListener() {
//                             @Override
//                             public void onClick(View v) {
//                                 Toast.makeText(
//                                         MainActivity.this,
//                                         "Toast comes out",
//                                         Toast.LENGTH_SHORT).show();
//                             }
//                         }).show();
//             }
//         });

         mViewPager = (ViewPager) findViewById(R.id.viewpager);
         setupViewPager();

    }
    
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    
    private void setupViewPager() {
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
}
