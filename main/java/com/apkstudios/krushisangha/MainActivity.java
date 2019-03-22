package com.apkstudios.krushisangha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getPreferences(0);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String log_out = bundle.getString("log_out");
            if(log_out!=null){
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
            }
        }

        jumpToActivity();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new FarmerFragment(),"FARMER");
        viewPagerAdapter.addFragments(new VendorFragment(),"VENDOR");
        viewPagerAdapter.addFragments(new ManagerFragment(),"MANAGER");
        viewPagerAdapter.addFragments(new StoreFragment(),"STORE");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //updated code
    }

    private void jumpToActivity() {

        String user_type = pref.getString(Constants.USER_TYPE,"");
        if(pref.getString(Constants.IS_LOGGED_IN,"").equals("true") && user_type.equals("farmer")){
            Bundle bundle = new Bundle();
            bundle.putString("is_logged_in",pref.getString(Constants.IS_LOGGED_IN,""));
            bundle.putString("email",pref.getString(Constants.EMAIL,""));
            bundle.putString("name",pref.getString(Constants.NAME,""));
            bundle.putString("mobile_number",pref.getString(Constants.MOBILE_NUMBER,""));
            Intent intent = new Intent(this,FarmerHomeActivity.class);
            intent.putExtras(bundle);
            //Log.i("myapp","ls "+pref.getString(Constants.IS_LOGGED_IN,""));
            startActivity(intent);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        String languageToLoad="en";

        switch (item.getItemId()) {
            case R.id.eng:
                languageToLoad = "en";
                break;
            case R.id.hi:
                languageToLoad = "hi";
                break;

            default:
                break;
        }

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
        return true;
    }

}

