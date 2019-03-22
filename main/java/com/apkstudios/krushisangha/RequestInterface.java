package com.apkstudios.krushisangha;

import com.apkstudios.krushisangha.models.ServerRequest;
import com.apkstudios.krushisangha.models.ServerResponse;
import com.apkstudios.krushisangha.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("/KrushiSangha/")
    Call<ServerResponse> operation(@Body ServerRequest request);

    @POST("/KrushiSangha/")
    Call<List<User>> operation2(@Body ServerRequest request);

    @GET("/KrushiSangha/")
    //Call<List<User>> getUsers();
    Call<List<User>> getUsers(@Body ServerRequest request);

}
