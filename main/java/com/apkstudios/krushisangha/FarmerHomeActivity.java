package com.apkstudios.krushisangha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apkstudios.krushisangha.models.Cards;
import com.apkstudios.krushisangha.models.CardsAdapter;
import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FarmerHomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    SharedPreferences pref;
    ProgressDialog viewProgressDialog;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapt;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Cards> list = null;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        viewProgressDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                loadList();
            }
        });

        drawerLayout = findViewById(R.id.drawer_farmer_home);
        navigationView = findViewById(R.id.navigation_view_farmer_home);

        View header = navigationView.getHeaderView(0);
        TextView headerName = header.findViewById(R.id.header_name);
        TextView headerEmail = header.findViewById(R.id.header_email);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        String name = bundle.getString("name");
        String mobile_number = bundle.getString("mobile_number");
        String taluka = bundle.getString("taluka");
        //Toast.makeText(this,taluka,Toast.LENGTH_LONG).show();

        //String lat = bundle.getString("lat");
        //String lng = bundle.getString("lng");

        pref = getPreferences(0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.EMAIL, email);
        editor.putString(Constants.NAME,name);
        editor.putString(Constants.MOBILE_NUMBER,mobile_number);
        editor.putString(Constants.TALUKA,taluka);
        //editor.putString(Constants.LAT,lat);
        //editor.putString(Constants.LNG,lng);
        editor.apply();

        headerEmail.setText(pref.getString(Constants.EMAIL,""));
        headerName.setText(pref.getString(Constants.NAME,""));
        //Snackbar.make(findViewById(R.id.drawer_farmer_home), lat+" "+lng, Snackbar.LENGTH_LONG).show();


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

                    /*case R.id.warehouse:
                        Bundle bundle = new Bundle();
                        bundle.putString("email",pref.getString(Constants.EMAIL,""));
                        //bundle.putString("lng",pref.getString(Constants.LNG,""));
                        Intent intent2 = new Intent(FarmerHomeActivity.this,NearestWarehouseActivity.class);
                        intent2.putExtras(bundle);
                        startActivity(intent2);
                        drawerLayout.closeDrawers();
                        return true;*/

                    case R.id.market_price:
                        Bundle bundle = new Bundle();
                        bundle.putString("url","http://agmarknet.gov.in/");
                        bundle.putString("activity","market_price");
                        Intent intent2 = new Intent(FarmerHomeActivity.this,WebViewActivity.class);
                        intent2.putExtras(bundle);
                        startActivity(intent2);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.weather:
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("url","http://city.imd.gov.in/citywx/localwx.php");
                        bundle3.putString("activity","weather");
                        Intent intent3 = new Intent(FarmerHomeActivity.this,WebViewActivity.class);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.search_commodity:
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("email",pref.getString(Constants.EMAIL,""));
                        bundle4.putString("taluka",pref.getString(Constants.TALUKA,""));
                        Intent intent5 = new Intent(FarmerHomeActivity.this,SearchCommodityActivity.class);
                        intent5.putExtras(bundle4);
                        startActivity(intent5);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.joined_community:
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("email",pref.getString(Constants.EMAIL,""));
                        Intent intent6 = new Intent(FarmerHomeActivity.this,JoinedCommunityActivity.class);
                        intent6.putExtras(bundle5);
                        startActivity(intent6);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.stores:
                        Bundle bundle6 = new Bundle();
                        bundle6.putString("email",pref.getString(Constants.EMAIL,""));
                        Intent intent7 = new Intent(FarmerHomeActivity.this,FarmerStoreActivity.class);
                        intent7.putExtras(bundle6);
                        startActivity(intent7);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.future_demand:
                        Bundle bundle7 = new Bundle();
                        bundle7.putString("email",pref.getString(Constants.EMAIL,""));
                        Intent intent8 = new Intent(FarmerHomeActivity.this,FarmerFutureDemandActivity.class);
                        intent8.putExtras(bundle7);
                        startActivity(intent8);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.notification:
                        Bundle bundle8 = new Bundle();
                        bundle8.putString("email",pref.getString(Constants.EMAIL,""));
                        Intent intent9 = new Intent(FarmerHomeActivity.this,FarmerNotificationActivity.class);
                        intent9.putExtras(bundle8);
                        startActivity(intent9);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.logout:
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("log_out","true");
                        Intent intent = new Intent(FarmerHomeActivity.this,MainActivity.class);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });


        //sendNotification();
        loadList();
    }

    private void loadList() {

        /*viewProgressDialog.setTitle("Getting Updates");
        viewProgressDialog.setMessage("Please wait while updates fetched");
        viewProgressDialog.setCanceledOnTouchOutside(false);
        viewProgressDialog.show();*/
        mSwipeRefreshLayout.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_UPDATES);
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<User>> call = requestInterface.operation2(request);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                list = null;
                list = new ArrayList<Cards>();
                for (User user : users) {
                    Cards cards = new Cards(user.getTitle(), user.getBody(), user.getType(), user.getCreated_at());
                    list.add(cards);
                }
                loadRecycler();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if(t!=null) {
                    Log.i("Myapp", t.getLocalizedMessage());
                    //viewProgressDialog.dismiss();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    void loadRecycler(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Collections.reverse(list);
        adapt =   new CardsAdapter(list,this);
        recyclerView.setAdapter(adapt);
        //viewProgressDialog.dismiss();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void sendNotification() {
        viewProgressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail("m2kunal1999@gmail.com");
        user.setTitle("Hello");
        user.setMessage("Welcome to KrushiSangha");
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.SEND_NOTIFICATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(findViewById(R.id.navigation_view_farmer_home), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                viewProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                viewProgressDialog.dismiss();
                Log.d(Constants.TAG,"failed");
                Snackbar.make(findViewById(R.id.navigation_view_farmer_home), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });


    }

    private void displayMessage(String s) {

        Snackbar.make(findViewById(R.id.drawer_farmer_home), s, Snackbar.LENGTH_LONG).show();

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

    @Override
    public void onRefresh() {
        loadList();
    }
}
