package com.app.flex.retrofit;

import android.support.annotation.CallSuper;

import com.app.flex.model.ForgotPassword;
import com.app.flex.model.Result;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

	@POST("auth/login")
	@FormUrlEncoded
	Call<Result> login(@Field("email") String email, @Field("password") String password, @Field("device_type") String device_type, @Field("device_token") String mdeviceToken);

	@POST("auth/register")
	@FormUrlEncoded
	Call<Result> register(@Field("email") String email, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("role") String role, @Field("device_type") String device_type,@Field("device_token") String deviceToken);


    @Multipart
	@POST("users/create_profile")
	Call<Result> createProfile(@Part("user_id") RequestBody user_id, @Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("phone_number") RequestBody phone_number, @Part("home_address") RequestBody home_address , @Part MultipartBody.Part file);



	@POST("auth/forgot_password")
	@FormUrlEncoded
	Call<ForgotPassword> forgotPassword(@Field("email") String email);

	@POST("users/change_password")
	@FormUrlEncoded
	Call<ForgotPassword> changePassword(@Field("user_id") String user_id, @Field("current_password") String current_password, @Field("password") String password, @Field("confirm_password") String confirm_password);


	@Multipart
	@POST("users/edit_profile")
	Call<Result> updateProfile(@Part("user_id") RequestBody user_id, @Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("phone_number") RequestBody phone_number, @Part("home_address") RequestBody home_address , @Part("office_address") RequestBody offfice_address ,@Part("email") RequestBody email,@Part MultipartBody.Part file);


}