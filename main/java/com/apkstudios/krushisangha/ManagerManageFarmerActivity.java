package com.apkstudios.krushisangha;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerManageFarmerActivity extends AppCompatActivity {

    private AppCompatButton btn_add;
    private EditText et_email_,et_A1_quality,et_A2_quality,et_A3_quality,et_cid;
    private ProgressBar progress;
    String email;
    String A1,A2,A3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_manage_farmer);

        btn_add =  findViewById(R.id.btn_Add);
        et_email_ =  findViewById(R.id.et_farmer_email);
        et_A1_quality =  findViewById(R.id.et_A1);
        et_A2_quality = findViewById(R.id.et_A2);
        et_A3_quality =  findViewById(R.id.et_A3);
        et_cid =  findViewById(R.id.et_cid);
        progress = findViewById(R.id.manager_manage_farmer_progress);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email_.getText().toString();
                String A1_quality = et_A1_quality.getText().toString();
                String A2_quality= et_A2_quality.getText().toString();
                String A3_quality = et_A3_quality.getText().toString();
                String cid = et_cid.getText().toString();
                if(!email.isEmpty() && !A1_quality.isEmpty() && !A2_quality.isEmpty() && !A3_quality.isEmpty()  && !cid.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    addProductProcess(email,A1_quality,A2_quality,A3_quality,cid);

                } else {

                    //Snackbar.make(v, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }

    private void addProductProcess(String email, String a1_quality, String a2_quality, String a3_quality, String cid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        A1 = a1_quality;
        A2 = a2_quality;
        A3 = a3_quality;

        User user = new User();
        user.setEmail(email);
        user.setA1(a1_quality);
        user.setA2(a2_quality);
        user.setA3(a3_quality);
        user.setCid(cid);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ADD_PRODUCT);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                sendNotification();
                Snackbar.make(findViewById(R.id.add_product_layout), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(findViewById(R.id.add_product_layout), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });


    }

    private void sendNotification() {
        //viewProgressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setTitle("Your Farm Produce is Submitted");
        user.setMessage("Quantity: A1="+A1+" A2="+A2+" A3="+A3);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.SEND_NOTIFICATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                //Snackbar.make(findViewById(R.id.navigation_view_farmer_home), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                //viewProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                //viewProgressDialog.dismiss();
                Log.d(Constants.TAG,"failed");
                //Snackbar.make(findViewById(R.id.navigation_view_farmer_home), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });


    }

}
