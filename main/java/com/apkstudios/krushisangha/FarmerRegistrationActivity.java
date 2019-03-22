package com.apkstudios.krushisangha;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FarmerRegistrationActivity extends AppCompatActivity {

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name,et_number,et_taluka;
    private ProgressBar progress;
    String newToken;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_registration);

        btn_register = (AppCompatButton) findViewById(R.id.btn_register);
        et_name = (EditText) findViewById(R.id.et_full_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_number = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        et_taluka = (EditText) findViewById(R.id.et_taluka);
        progress = (ProgressBar) findViewById(R.id.progress);
        v = findViewById(R.id.farmer_reg_layout);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String number = et_number.getText().toString();
                String taluka = et_taluka.getText().toString();
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( FarmerRegistrationActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        newToken = instanceIdResult.getToken();

                    }
                });

                if(newToken != null){
                Toast.makeText(FarmerRegistrationActivity.this,newToken,Toast.LENGTH_LONG).show();
                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !number.isEmpty() && !taluka.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name,email,password,number,taluka,newToken);

                } else {

                    Snackbar.make(v, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }}
            }


        });
    }

    private void registerProcess(String name, String email, String password, String number, String taluka, String newToken) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setMobileNumber(number);
        user.setTaluka(taluka);
        user.setNewToken(newToken);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.FARMER_REGISTER);
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
