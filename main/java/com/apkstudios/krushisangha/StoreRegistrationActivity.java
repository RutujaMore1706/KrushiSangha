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

public class StoreRegistrationActivity extends AppCompatActivity {

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name,et_number,et_taluka,et_lic_num;
    private ProgressBar progress;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_registration);

        btn_register = (AppCompatButton) findViewById(R.id.btn_register);
        et_name = (EditText) findViewById(R.id.et_store_name);
        et_lic_num = (EditText) findViewById(R.id.et_lic_num);
        et_email = (EditText) findViewById(R.id.et_email);
        et_number = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        et_taluka = (EditText) findViewById(R.id.et_taluka);
        progress = (ProgressBar) findViewById(R.id.progress);
        v = findViewById(R.id.store_reg_layout);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String lic_num = et_lic_num.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String number = et_number.getText().toString();
                String taluka = et_taluka.getText().toString();

                if(!name.isEmpty() && !lic_num.isEmpty() && !email.isEmpty() && !password.isEmpty() && !number.isEmpty() && !taluka.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name,lic_num,email,password,number,taluka);

                } else {

                    Snackbar.make(v, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
            }


        });
    }

    private void registerProcess(String name,String lic_num, String email, String password, String number, String taluka) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setLic_num(lic_num);
        user.setEmail(email);
        user.setPassword(password);
        user.setMobileNumber(number);
        user.setTaluka(taluka);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.STORE_REGISTER);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(v, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(v, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }
}
