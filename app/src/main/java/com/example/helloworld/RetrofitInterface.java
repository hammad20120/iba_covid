package com.example.helloworld;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @GET("medorgs/getDetails/")
    Call<List<MedOrganization>> getDetails();



}
