package com.example.helloworld;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @GET("medorgs/getDetails/")
    Call<List<MedOrganization>> getDetails();

    @POST("medorgs/addOrganization/")
    Call<ResponseBody> addOrganization(@Body MedOrganization medOrganization);

    @POST("users/register/")
    Call<ResponseBody> register(@Body User user);

    @POST("users/login/")
    Call<ResponseBody> login(@Body User user);


}
