package com.example.testapiimagebytecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ImageApi imageApi;
    Retrofit retrofit;
    private CategoryAdapter categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.101:8019/")
                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
                .build();
        imageApi = retrofit.create(ImageApi.class);

        getImage();
//        getCategory();
    }

    private void getCategory() {
        Call<List<Category>> categoryCall = imageApi.getCategory();
        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Minh", response.code() + " Code ");
                    return;
                }
                List<Category> data = response.body();
                categoryAdapter = new CategoryAdapter(data);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    private void getImage() {
        Call<List<Image>> getImage = imageApi.getListImage();
        getImage.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Minh", response.code() + " Code ");
                    return;
                }

                List<Image> data = response.body();
                Log.e("Minh" , "data size "  +data.get(0).getImage() );
//                Log.e("zzzzzzzzzzzzzzzzz" , data.get(0).getId() + " z");
                imageAdapter = new ImageAdapter(data);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(imageAdapter);
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                Log.e("Minh", t.getMessage() + " Code ");
            }
        });
    }
}