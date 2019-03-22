package com.apkstudios.krushisangha;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.apkstudios.krushisangha.models.Cards;
import com.apkstudios.krushisangha.models.CardsAdapter;
import com.apkstudios.krushisangha.models.FutureDemandCards;
import com.apkstudios.krushisangha.models.FutureDemandCardsAdapter;
import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FarmerNotificationActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    RecyclerView.Adapter adapt;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Cards> list = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar toolbar;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_notification);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");

        toolbar = findViewById(R.id.farmer_notification_toolbar);
        toolbar.setTitle(getString(R.string.notification));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_farmer_notification);
        mSwipeRefreshLayout = findViewById(R.id.swipe_container_farmer_notification);
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
    }

    private void loadList() {

        mSwipeRefreshLayout.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user = new User();
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_NOTIFICATION);
        request.setUser(user);
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<User>> call = requestInterface.operation2(request);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                list = null;
                list = new ArrayList<Cards>();
                for (User user : users) {
                    Cards cards = new Cards(user.getTitle(), user.getBody(), "NULL", user.getCreated_at());
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


    @Override
    public void onRefresh() {
        loadList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
