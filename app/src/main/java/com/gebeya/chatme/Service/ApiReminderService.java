package com.gebeya.chatme.Service;

import com.gebeya.chatme.model.Reminder;
import com.gebeya.chatme.model.ReminderRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiReminderService {


//    @FormUrlEncoded
//    @POST("/chat/{dialog}")
//    Call<ResponseBody> createReminder(
//
//            @Field("speech") String speech
//
//    );

//    @FormUrlEncoded
    @POST("/chat/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Reminder> createReminder(

            @Body ReminderRequest reminderRequest,
            @Header("Authorization") String authToken

            );

    @FormUrlEncoded
    @POST("/chat/{dialog}")
    Call<ResponseBody> cancleReminder(

            @Field("speech") String speech

    );

}
