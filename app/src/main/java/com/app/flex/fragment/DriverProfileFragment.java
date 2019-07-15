package com.app.flex.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.flex.R;
import com.app.flex.Utils.PrefUtils;
import com.app.flex.model.User;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverProfileFragment extends Fragment
{
	private Button mEditProfileBtn;
	CircleImageView mProfileIv;
	TextView mUserNameTv, mUserEmailTv, mUserPhoneNoTv, mHomeAddressTv, mOfficeAddressTv;
	User user;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View rootview=inflater.inflate(R.layout.fragment_driver_profile,null);

		mProfileIv = rootview.findViewById(R.id.profile_image);
		mUserNameTv = rootview.findViewById(R.id.user_name_tv);
		mUserEmailTv = rootview.findViewById(R.id.user_email_tv);
		mUserPhoneNoTv = rootview.findViewById(R.id.user_phone_no_tv);
		mHomeAddressTv = rootview.findViewById(R.id.home_address_tv);
		mOfficeAddressTv = rootview.findViewById(R.id.office_address_tv);

		PrefUtils.initPreferance(getActivity());
		user = PrefUtils.getUserInfo();

		if(user != null){
			Glide.with(getActivity()).load(user.getmProfilePicture()).into(mProfileIv);
			mUserNameTv.setText(user.getmFirstname() +" "+ user.getmLastName());
			mUserEmailTv.setText(user.getmEmail());
			mUserPhoneNoTv.setText(user.getmPhoneNumber());
			mHomeAddressTv.setText(user.getmHomeAddress());
			mOfficeAddressTv.setText(user.getmOfficeAddress());
		}

		mEditProfileBtn=rootview.findViewById(R.id.edit_profile_btn);
		mEditProfileBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditProfileFragment someFragment = new EditProfileFragment();
				FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});

		return rootview;
	}
}
