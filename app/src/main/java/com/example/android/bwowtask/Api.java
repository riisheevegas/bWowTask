package com.example.android.bwowtask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("b/60816ce39a9aa933335504a8")
    Call<JSONResponse> getData();
}
