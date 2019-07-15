package com.app.flex.retrofit;

import android.support.annotation.NonNull;

import com.app.flex.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
 
public class ApiManager {
    private static String BaseUrl = "http://php.rlogical.com/flexinc/api/";
 
    public static ApiService getService() {

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Apikey", "bGv2HV8HYkwXN73hbC9Bcv8FUVac99x3ZVz33HpgWKk7CFtp")
                        .build();
                return chain.proceed(request);
            }
        })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
 
       return retrofit.create(ApiService.class);
    }

}