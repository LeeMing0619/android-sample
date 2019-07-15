package com.app.flex;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.flex.Utils.AppUtils;
import com.app.flex.Utils.PrefUtils;
import com.app.flex.fragment.profile.UploadProfileInfoActivity;
import com.app.flex.model.Result;
import com.app.flex.model.User;
import com.app.flex.retrofit.ApiManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfileActivity extends AppCompatActivity {
	RelativeLayout mTopRl;
	Button mSaveProfileBtn;
	CircleImageView mProfileIv;
	EditText mFirstNameEt, mLastnameEt, mLoactionEt, mPhoneNumberEt;
	User user;
	RequestBody user_id, firstname, lastName, phoneNumber, location;
	File image_file;
	ProgressDialog pd;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_create_profile);

		mTopRl = findViewById(R.id.top_rl);
		mProfileIv = findViewById(R.id.profile_image);

		mFirstNameEt = findViewById(R.id.first_name_et);
		mLastnameEt = findViewById(R.id.last_name_et);
		mLoactionEt = findViewById(R.id.location_et);
		mPhoneNumberEt = findViewById(R.id.phone_number_et);


		PrefUtils.initPreferance(this);
		user = PrefUtils.getUserInfo();
		pd = new ProgressDialog(this);



		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				(int) (AppUtils.getDeviceHeight(this) / 2.5f));
		mTopRl.setLayoutParams(params);


		mSaveProfileBtn = findViewById(R.id.save_profile_btn);
		mSaveProfileBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mFirstNameEt.getText().toString().isEmpty()) {
					Toast.makeText(CreateProfileActivity.this, "Please Enter Firstname", Toast.LENGTH_SHORT).show();
				} else if (mLastnameEt.getText().toString().isEmpty()) {
					Toast.makeText(CreateProfileActivity.this, "Please Enter Lastname", Toast.LENGTH_SHORT).show();
				} else if (mLoactionEt.getText().toString().isEmpty()) {
					Toast.makeText(CreateProfileActivity.this, "Please Enter Location", Toast.LENGTH_SHORT).show();
				} else if (mPhoneNumberEt.getText().toString().isEmpty()) {
					Toast.makeText(CreateProfileActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
				} else {
					//File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");

					user_id = RequestBody.create(MediaType.parse("text/plain"), "16");
					firstname = RequestBody.create(MediaType.parse("text/plain"), mFirstNameEt.getText().toString());
					lastName = RequestBody.create(MediaType.parse("text/plain"), mLastnameEt.getText().toString());
					phoneNumber = RequestBody.create(MediaType.parse("text/plain"), mPhoneNumberEt.getText().toString());
					location = RequestBody.create(MediaType.parse("text/plain"), mLoactionEt.getText().toString());
					doCreateProfile(user_id, firstname, lastName, phoneNumber, location, image_file);
				}

			}
		});

		mProfileIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent_home = new Intent(CreateProfileActivity.this, UploadProfileInfoActivity.class);
				startActivity(intent_home);
				TedPermission.with(CreateProfileActivity.this)
						.setPermissionListener(permissionlistener)
						.setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
						.setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
						.check();
				/*finish();*/
			}
		});



	}

	PermissionListener permissionlistener = new PermissionListener() {
		@Override
		public void onPermissionGranted() {

		}

		@Override
		public void onPermissionDenied(List<String> deniedPermissions) {

		}
	};

	@Override
	protected void onResume() {
		super.onResume();

		if(!MyApplication.profileURI.equals("")){
			image_file=new File(MyApplication.profileURI);
			//mProfileIv.setImageURI(Uri.fromFile(image_file));
			Glide.with(this).load(MyApplication.profileDisplayURI).error(R.drawable.user_default).into(mProfileIv);
		}

	}

	private void doCreateProfile(RequestBody user_id, RequestBody first_name, RequestBody last_name, RequestBody phone_number, RequestBody home_address, File profile_image) {

		pd.show();
		pd.setMessage("Loading");
		ApiManager.getService().createProfile(user_id, first_name, last_name, phone_number, home_address, uploadImage(profile_image)).enqueue(new Callback<Result>() {
			@Override
			public void onResponse(Call<Result> call, Response<Result> response) {

				if (response != null && response.body() != null) {

					Result result = response.body();

					if (result.getStatus().equalsIgnoreCase("success")) {
						PrefUtils.addUserInfo(result.getUser());
						Intent i = new Intent(CreateProfileActivity.this, TermnConditionActivity.class);
						startActivity(i);
						finish();
					}

				} else {
					Toast.makeText(CreateProfileActivity.this, "Create Profile Failed", Toast.LENGTH_SHORT).show();
				}

				pd.cancel();
			}

			@Override
			public void onFailure(Call<Result> call, Throwable t) {
				pd.cancel();
				Toast.makeText(CreateProfileActivity.this, "Create Profile Failed", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public MultipartBody.Part uploadImage(File file) {
		RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
		MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);

		return body;
	}
}
