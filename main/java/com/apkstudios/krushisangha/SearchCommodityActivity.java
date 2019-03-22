package com.apkstudios.krushisangha;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchCommodityActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;
    SharedPreferences pref;
    Button see_warehouse_btn,join_group_btn;
    String email,taluka, selected;
    TextView last_year_profit,total_farmers,nearest_warehouse;
    ProgressDialog viewProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_commodity);

        toolbar = findViewById(R.id.toolbar_search_commodity);
        toolbar.setTitle(getString(R.string.search_commodity));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        taluka = bundle.getString("taluka");

        pref = getPreferences(0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.EMAIL, email);
        editor.putString(Constants.TALUKA,taluka);
        editor.apply();

        see_warehouse_btn = findViewById(R.id.nearest_warehouse_btn);
        see_warehouse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("email",email);
                Intent intent = new Intent(SearchCommodityActivity.this,NearestWarehouseActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });

        join_group_btn = findViewById(R.id.join_group_btn);
        join_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestInterface requestInterface = retrofit.create(RequestInterface.class);

                User user = new User();
                user.setEmail(email);
                user.setTaluka(taluka);
                user.setProduct(selected);
                ServerRequest request = new ServerRequest();
                request.setOperation(Constants.JOIN_COMMUNITY);
                request.setUser(user);
                Call<ServerResponse> response = requestInterface.operation(request);

                response.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                        ServerResponse resp = response.body();
                        if(resp.getResult().equals("success")) {
                            join_group_btn.setVisibility(View.INVISIBLE);
                            Toast.makeText(SearchCommodityActivity.this,"Community Joined Successfully",Toast.LENGTH_LONG).show();
                        }
                        viewProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                        Log.d(Constants.TAG,"failed");
                        Toast.makeText(SearchCommodityActivity.this,"Failed to Join community",Toast.LENGTH_LONG).show();
                        viewProgressDialog.dismiss();
                    }
                });
            }
        });
        last_year_profit = findViewById(R.id.last_year_profit);
        total_farmers = findViewById(R.id.total_farmer);
        nearest_warehouse = findViewById(R.id.see_warehouse);
        spinner = findViewById(R.id.spinner_commodity);

        viewProgressDialog = new ProgressDialog(this);
        viewProgressDialog.setTitle("Fetching Community Details");
        viewProgressDialog.setMessage("Please wait while community details get fetched");
        viewProgressDialog.setCanceledOnTouchOutside(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.commodity,
                R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                selected = parentView.getItemAtPosition(position).toString();

                switch (position) {
                    case 0:

                        break;
                    case 1:
                        getCommunity(selected);
                        //Toast.makeText(SearchCommodityActivity.this,taluka,Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        getCommunity(selected);
                        //Toast.makeText(SearchCommodityActivity.this,selected,Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getCommunity(String selected) {
        viewProgressDialog.show();
        join_group_btn.setVisibility(View.INVISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(pref.getString(Constants.EMAIL,""));
        user.setTaluka(pref.getString(Constants.TALUKA,""));
        user.setProduct(selected);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_COMMUNITY);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                see_warehouse_btn.setVisibility(View.VISIBLE);
                nearest_warehouse.setVisibility(View.VISIBLE);
                last_year_profit.setVisibility(View.VISIBLE);
                total_farmers.setVisibility(View.VISIBLE);

                if(resp.getUser().getEmail()!=null) {
                    if (!(resp.getUser().getEmail().equals("true"))) {
                        join_group_btn.setVisibility(View.VISIBLE);
                    }
                }else{
                    join_group_btn.setVisibility(View.VISIBLE);
                }
                total_farmers.setText(getString(R.string.total_farmer)+resp.getUser().getTotal_farmer());
                viewProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                Snackbar.make(findViewById(R.id.search_community_layout), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                viewProgressDialog.dismiss();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
