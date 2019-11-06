package com.gebeya.chatme;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PUT;

public class RetrofitClient {

    private static final String BASE_URL = "http://d64eb6f9.ngrok.io/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){

        if (mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public ApiUserService getUserServiceApi(){
        return retrofit.create(ApiUserService.class);
    }

    public ApiReminderService getReminderServiceApi(){
        return retrofit.create(ApiReminderService.class);
    }

}
