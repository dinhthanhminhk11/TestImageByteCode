package com.example.testapiimagebytecode;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageApi {

    @GET("admin/api/products")
    Call<List<Image>> getListImage();

    @GET("admin/api/categories")
    Call<List<Category>> getCategory();
}
