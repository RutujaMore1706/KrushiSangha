package com.apkstudios.krushisangha;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearestWarehouseActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ProgressDialog viewProgressDialog;
    SharedPreferences pref;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_warehouse);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        pref = getPreferences(0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.EMAIL,email);
        editor.apply();

        viewProgressDialog = new ProgressDialog(this);
        viewProgressDialog.setTitle("Fetching Nearest Warehouse");
        viewProgressDialog.setMessage("Please wait while records get fetched");
        viewProgressDialog.setCanceledOnTouchOutside(false);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //Snackbar.make(findViewById(R.id.map), pref.getString(Constants.EMAIL,""), Snackbar.LENGTH_LONG).show();
        loadList();
        getLatLng();
    }

    private void getLatLng() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(pref.getString(Constants.EMAIL,""));
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_FARMER_LOC);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                    Double lat = Double.parseDouble(resp.getResult());
                    Double lng = Double.parseDouble(resp.getMessage());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),10));

                    viewProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                Snackbar.make(findViewById(R.id.map), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                viewProgressDialog.dismiss();
            }
        });

    }

    private void loadList() {

        viewProgressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.SHOW_WAREHOUSE);
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<User>> call = requestInterface.operation2(request);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                User u;
                for (User user : users) {
                    u = new User();
                    u.setWh_name(user.getWh_name());
                    u.setLat(user.getLat());
                    u.setLng(user.getLng());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(user.getLat(),user.getLng())).title(user.getWh_name()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                }
                //Double lat = Double.parseDouble(pref.getString(Constants.LAT,""));
                //Double lng = Double.parseDouble(pref.getString(Constants.LNG,""));
                //mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),10));

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if(t!=null) {
                    Log.i("Myapp", t.getLocalizedMessage());
                }
            }
        });

    }
}
