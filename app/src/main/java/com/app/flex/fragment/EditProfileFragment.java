package com.app.flex.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flex.MyApplication;
import com.app.flex.R;
import com.app.flex.TermnConditionActivity;
import com.app.flex.Utils.AppUtils;
import com.app.flex.Utils.PrefUtils;
import com.app.flex.model.Result;
import com.app.flex.model.User;
import com.app.flex.retrofit.ApiManager;
import com.bumptech.glide.Glide;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
	CircleImageView mProfileIv;
	TextView mUserNameTv;
	EditText mFirstNameEt, mLastNameEt, mEmailEt, mPhoneNoEt, mHomeAddressEt, mOfficeAddressEt;
	User user;
	ProgressDialog pd;

	Button mSaveChanges;
	ImageView mEditIv;
	String image_uri;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootiview = inflater.inflate(R.layout.fragment_edit_profile, null);

		pd = new ProgressDialog(getActivity());

		mSaveChanges = rootiview.findViewById(R.id.edit_profile_btn);

		mEditIv = rootiview.findViewById(R.id.edit_image_iv);
		mEditIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});

		mProfileIv = rootiview.findViewById(R.id.profile_image);
		mUserNameTv = rootiview.findViewById(R.id.user_name_tv);
		mFirstNameEt = rootiview.findViewById(R.id.first_name_et);
		mLastNameEt = rootiview.findViewById(R.id.last_name_et);
		mEmailEt = rootiview.findViewById(R.id.email_et);
		mPhoneNoEt = rootiview.findViewById(R.id.phone_no_et);
		mHomeAddressEt = rootiview.findViewById(R.id.home_address_et);
		mOfficeAddressEt = rootiview.findViewById(R.id.office_address_et);

		PrefUtils.initPreferance(getActivity());
		user = PrefUtils.getUserInfo();

		if (user != null) {
			Glide.with(getActivity()).load(user.getmProfilePicture()).into(mProfileIv);
			mUserNameTv.setText(user.getmFirstname() + " " + user.getmLastName());
			mFirstNameEt.setText(user.getmFirstname());
			mLastNameEt.setText(user.getmLastName());
			mEmailEt.setText(user.getmEmail());
			mPhoneNoEt.setText(user.getmPhoneNumber());
			mHomeAddressEt.setText(user.getmHomeAddress());
			mOfficeAddressEt.setText(user.getmOfficeAddress());
		}

		mSaveChanges.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mFirstNameEt.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "First name must ne entered.", Toast.LENGTH_SHORT).show();
				} else if (mLastNameEt.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Last name must ne entered.", Toast.LENGTH_SHORT).show();
				} else if (mEmailEt.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Email name must ne entered.", Toast.LENGTH_SHORT).show();
				} else if (mPhoneNoEt.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Phone number must ne entered.", Toast.LENGTH_SHORT).show();
				} else if (mHomeAddressEt.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Home Address must ne entered.", Toast.LENGTH_SHORT).show();
				} else if (mOfficeAddressEt.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Office Address must ne entered.", Toast.LENGTH_SHORT).show();
				} else {

					doCreateProfile(user.getmId(),mFirstNameEt.getText().toString(),mLastNameEt.getText().toString(),mEmailEt.getText().toString(),
							mPhoneNoEt.getText().toString(),mHomeAddressEt.getText().toString(),mOfficeAddressEt.getText().toString(),image_uri);
				}
			}
		});

		return rootiview;
	}

	private void doCreateProfile(String mUserId, String mFirstName, String mLastName, String mEmail, String mPhoneNumber, String mHomeAddress, String mOfficeAddress, String profile_image) {

		RequestBody mUserRb = RequestBody.create(MediaType.parse("text/plain"), mUserId);
		RequestBody mFirstNameRb = RequestBody.create(MediaType.parse("text/plain"), mFirstName);
		RequestBody mLastNameRb = RequestBody.create(MediaType.parse("text/plain"), mLastName);
		RequestBody mEmailRb = RequestBody.create(MediaType.parse("text/plain"), mEmail);
		RequestBody mPhoneNumberRb = RequestBody.create(MediaType.parse("text/plain"), mPhoneNumber);
		RequestBody mHomeAddressRb = RequestBody.create(MediaType.parse("text/plain"), mHomeAddress);
		RequestBody mOfficeAddressRb = RequestBody.create(MediaType.parse("text/plain"), mOfficeAddress);

		File image_file=new File(profile_image);
		RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), profile_image);
		MultipartBody.Part mImageBody = MultipartBody.Part.createFormData("profile_image", image_file.getName(), requestFile);

		pd.show();
		pd.setMessage("Loading");

		ApiManager.getService().updateProfile(mUserRb, mFirstNameRb, mLastNameRb, mPhoneNumberRb , mHomeAddressRb, mOfficeAddressRb, mEmailRb, mImageBody).enqueue(new Callback<Result>() {
			@Override
			public void onResponse(Call<Result> call, Response<Result> response) {

				if (response != null && response.body() != null) {

					Result result = response.body();

					if (result.getStatus().equalsIgnoreCase("success")) {
						PrefUtils.addUserInfo(result.getUser());
						Intent i = new Intent(getActivity(), TermnConditionActivity.class);
						startActivity(i);
					}

				} else {
					Toast.makeText(getActivity(), "Create Profile Failed", Toast.LENGTH_SHORT).show();
				}

				pd.cancel();
			}

			@Override
			public void onFailure(Call<Result> call, Throwable t) {
				pd.cancel();
				Toast.makeText(getActivity(), "Create Profile Failed", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void selectImage() {
		final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
						ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
					} else {
						Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
						Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), file);
						m_intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
						m_intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
						m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
						startActivityForResult(m_intent, 1);
					}


				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1) {
				File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
				image_uri = file.toString();
				Glide.with(getActivity()).load(file).into(mProfileIv);
			} else if (requestCode == 2) {
				if (data != null) {
					image_uri = AppUtils.getPathFromURI(getActivity(), data.getData());
					mProfileIv.setImageURI(Uri.fromFile(new File(image_uri)));
				}
			}
		}
	}
}
