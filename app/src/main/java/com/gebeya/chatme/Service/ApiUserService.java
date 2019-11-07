package com.gebeya.chatme.Service;

import com.gebeya.chatme.model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiUserService {

    @FormUrlEncoded
    @POST("/user/register")
    Call<ResponseBody> signUp(

            @Field("name") String name,
            @Field("phoneNumber") Long phone
    );

//    @FormUrlEncoded
//    @GET("/user/login") Call<ResponseBody> getTokenfromApi(
//            @Header("Authorization") String authToken
//    );

    @FormUrlEncoded
    @POST("/user/login")
    Call<LoginResponse> logIn(
            @Field("phoneNumber") Long phone
    );


}
