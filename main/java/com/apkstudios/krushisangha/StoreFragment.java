package com.apkstudios.krushisangha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StoreFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton btn_login;
    private EditText et_email,et_password;
    private TextView tv_register;
    private ProgressBar progress;
    private SharedPreferences pref;

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        tv_register = view.findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);

        pref = getActivity().getPreferences(0);

        btn_login = view.findViewById(R.id.btn_login);

        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);

        progress = view.findViewById(R.id.progress);

        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                Intent intent = new Intent(getActivity(),StoreRegistrationActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    loginProcess(email,password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void loginProcess(String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.STORE_LOGIN);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.IS_LOGGED_IN,"true");
                    editor.putString(Constants.USER_TYPE,"store");
                    editor.putString(Constants.EMAIL,resp.getUser().getEmail());
                    editor.putString(Constants.NAME,resp.getUser().getName());
                    editor.putString(Constants.MOBILE_NUMBER,resp.getUser().getMobileNumber());
                    editor.apply();
                    goToProfile(getView());

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }

        });
    }

    private void goToProfile(View view){
        Bundle bundle = new Bundle();
        bundle.putString("is_logged_in",pref.getString(Constants.IS_LOGGED_IN,""));
        bundle.putString("user_type",pref.getString(Constants.USER_TYPE,""));
        bundle.putString("email",pref.getString(Constants.EMAIL,""));
        bundle.putString("name",pref.getString(Constants.NAME,""));
        bundle.putString("mobile_number",pref.getString(Constants.MOBILE_NUMBER,""));
        Intent intent = new Intent(getActivity(),StoreHomeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().finish();
    }

}
