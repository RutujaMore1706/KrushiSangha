package com.apkstudios.krushisangha;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apkstudios.krushisangha.models.Cards;
import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FarmerStoreActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner type_spinner, item_spinner;
    SharedPreferences pref;
    TextView price, min, max;
    EditText quantity;
    Button order_btn;
    String selected,selected2, email;
    ArrayList<String> item = null;
    ProgressDialog viewProgressDialog;
    TextInputLayout select_quantity;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_store);

        toolbar = findViewById(R.id.toolbar_farmer_store);
        toolbar.setTitle(getString(R.string.stores));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");

        price = findViewById(R.id.price_tv);
        min = findViewById(R.id.min_tv);
        max = findViewById(R.id.max_tv);
        quantity = findViewById(R.id.et_select_quantity);
        order_btn = findViewById(R.id.order_btn);
        select_quantity = findViewById(R.id.til_select_quantity);

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderProduct();
            }
        });

        type_spinner = findViewById(R.id.spinner_farmer_item_type);
        item_spinner = findViewById(R.id.spinner_farmer_item);
        viewProgressDialog = new ProgressDialog(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.item_type,
                R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        type_spinner.setAdapter(adapter);
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                selected = parentView.getItemAtPosition(position).toString();

                switch (position) {
                    case 0:

                        break;
                    case 1:
                        getItems(selected);
                        //Toast.makeText(SearchCommodityActivity.this,taluka,Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        getItems(selected);
                        //Toast.makeText(SearchCommodityActivity.this,selected,Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        getItems(selected);
                        //Toast.makeText(SearchCommodityActivity.this,selected,Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        getItems(selected);
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

       item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selected2 = parent.getItemAtPosition(position).toString();
               getItemDetails(selected2);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }

    private void orderProduct() {

        viewProgressDialog.setTitle("Processing Order");
        viewProgressDialog.setMessage("Please wait while Order Processed");
        viewProgressDialog.setCanceledOnTouchOutside(false);
        viewProgressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user = new User();
        user.setEmail(email);
        user.setQuantity(quantity.getText().toString());
        user.setType(selected);
        user.setName(selected2);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ORDER_ITEM);
        request.setUser(user);
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals("success")){
                    Snackbar.make(findViewById(R.id.farmer_store_layout), "Order Placed Successfully", Snackbar.LENGTH_LONG).show();
                }
                viewProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                viewProgressDialog.dismiss();
                Log.d(Constants.TAG,"failed");
                //Snackbar.make(findViewById(R.id.navigation_view_farmer_home), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }

    private void getItemDetails(String selected2) {

        viewProgressDialog.setTitle("Fetching Items Details");
        viewProgressDialog.setMessage("Please wait while Items get fetched");
        viewProgressDialog.setCanceledOnTouchOutside(false);
        viewProgressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user = new User();
        user.setType(selected2);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_ITEM_DETAILS);
        request.setUser(user);
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                price.setVisibility(View.VISIBLE);
                min.setVisibility(View.VISIBLE);
                min.setText(getText(R.string.min)+" "+resp.getUser().getMin());
                max.setVisibility(View.VISIBLE);
                max.setText(getText(R.string.max)+" "+resp.getUser().getMax());
                select_quantity.setVisibility(View.VISIBLE);
                order_btn.setVisibility(View.VISIBLE);
                viewProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                viewProgressDialog.dismiss();
                Log.d(Constants.TAG,"failed");
                //Snackbar.make(findViewById(R.id.navigation_view_farmer_home), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }

    private void getItems(String selected) {

        viewProgressDialog.setTitle("Fetching Items Details");
        viewProgressDialog.setMessage("Please wait while Items get fetched");
        viewProgressDialog.setCanceledOnTouchOutside(false);
        viewProgressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user = new User();
        user.setType(selected);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_ITEM);
        request.setUser(user);
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<User>> call = requestInterface.operation2(request);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                item = new ArrayList<String>();
                item.add("SELECT ITEM");
                for (User user : users) {
                    item.add(user.getName());
                    //Toast.makeText(getApplicationContext(),item[i],Toast.LENGTH_LONG).show();
                    i++;
                }
                item_spinner.setVisibility(View.VISIBLE);
                loadItemSpinner();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if(t!=null) {
                    Log.i("Myapp", t.getLocalizedMessage());
                    viewProgressDialog.dismiss();
                }
            }
        });

    }

    private void loadItemSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item , item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        item_spinner.setAdapter(adapter);

        viewProgressDialog.dismiss();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
