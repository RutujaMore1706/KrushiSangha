package com.apkstudios.krushisangha;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.apkstudios.krushisangha.models.CommunityCards;
import com.apkstudios.krushisangha.models.CommunityCardsAdapter;
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

public class JoinedCommunityActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar toolbar;
    SharedPreferences pref;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapt;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CommunityCards> list = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_community);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        Toast.makeText(this,email,Toast.LENGTH_LONG).show();

        toolbar = findViewById(R.id.toolbar_joined_community);
        toolbar.setTitle(getString(R.string.joined_community));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_joined_community);
        mSwipeRefreshLayout = findViewById(R.id.swipe_joined_community);
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

    public void loadList() {

        mSwipeRefreshLayout.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user = new User();
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_FULL_COMMUNITY);
        request.setUser(user);
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<User>> call = requestInterface.operation2(request);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                list = null;
                list = new ArrayList<CommunityCards>();
                for (User user : users) {
                    CommunityCards cards = new CommunityCards(user.getProduct(), user.getCurrent_price(),user.getMin(), user.getMax(), user.getQuantity());
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

    public void loadRecycler(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Collections.reverse(list);
        adapt =   new CommunityCardsAdapter(list,this);
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
