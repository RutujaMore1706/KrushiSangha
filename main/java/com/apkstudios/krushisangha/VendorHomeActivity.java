package com.apkstudios.krushisangha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class VendorHomeActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_vendor_home);
        navigationView = (NavigationView) findViewById(R.id.navigation_view_vendor_home);

        View header = navigationView.getHeaderView(0);
        TextView headerName = header.findViewById(R.id.header_name);
        TextView headerEmail = header.findViewById(R.id.header_email);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        String name = bundle.getString("name");
        String mobile_number = bundle.getString("mobile_number");
        pref = getPreferences(0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.EMAIL, email);
        editor.putString(Constants.NAME,name);
        editor.putString(Constants.MOBILE_NUMBER,mobile_number);
        editor.apply();
        headerEmail.setText(pref.getString(Constants.EMAIL,""));
        headerName.setText(pref.getString(Constants.NAME,""));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        item.setChecked(true);
                        displayMessage("Home Selected...");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.profile:
                        item.setChecked(true);
                        displayMessage("Profile Selected...");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.my_product:
                        item.setChecked(true);
                        displayMessage("My Product Selected...");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.notification:
                        item.setChecked(true);
                        displayMessage("Notification Selected...");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.logout:
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                        Bundle bundle = new Bundle();
                        bundle.putString("log_out","true");
                        Intent intent = new Intent(VendorHomeActivity.this,MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    private void displayMessage(String s) {

        Snackbar.make(findViewById(R.id.drawer_vendor_home), s, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
