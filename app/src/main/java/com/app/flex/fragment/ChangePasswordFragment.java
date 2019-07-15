package com.app.flex.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flex.ForgotPasswordActivity;
import com.app.flex.R;
import com.app.flex.Utils.PrefUtils;
import com.app.flex.model.ForgotPassword;
import com.app.flex.model.User;
import com.app.flex.retrofit.ApiManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordFragment extends Fragment {
    TextView mUserNameTv;
    EditText mOldPasswordEt, mNewPaswordEt;
    Button mSaveChangesBtn;
    ProgressDialog pd;
    User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_change_password, container, false);

        pd = new ProgressDialog(getActivity());
        mUserNameTv = rootview.findViewById(R.id.user_name_tv);
        mOldPasswordEt = rootview.findViewById(R.id.old_password_et);
        mNewPaswordEt = rootview.findViewById(R.id.new_password_et);
        mSaveChangesBtn = rootview.findViewById(R.id.save_changes_btn);

        PrefUtils.initPreferance(getActivity());
        user = PrefUtils.getUserInfo();

        if(user != null){
            mUserNameTv.setText(user.getmFirstname() + " " + user.getmLastName());
        }

        mSaveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOldPasswordEt.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please Enter Old Password", Toast.LENGTH_SHORT).show();
                }else if(mNewPaswordEt.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please Enter New Password", Toast.LENGTH_SHORT).show();
                }else if(!mOldPasswordEt.getText().toString().equals(mNewPaswordEt.getText().toString())){
                    Toast.makeText(getActivity(), "Please Enter correct New password", Toast.LENGTH_SHORT).show();
                }else {
                    changePasswordApi(user.getmId(), "12345678", mOldPasswordEt.getText().toString(), mNewPaswordEt.getText().toString());
                }
            }
        });


        return rootview;
    }

    private void changePasswordApi(String user_id, String current_password, String password, String confirm_password) {

        pd.show();
        pd.setMessage("Loading");

        ApiManager.getService().changePassword(user_id, current_password, password, confirm_password).enqueue(new Callback<ForgotPassword>() {

            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                pd.cancel();
                if (response != null && response.body() != null) {

                    ForgotPassword result = response.body();
                    Toast.makeText(getActivity(),result.getmResult(),Toast.LENGTH_SHORT).show();

                    if(result.getmStatus().equals("success"))
                    {
                        //finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                pd.cancel();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
