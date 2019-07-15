package com.app.flex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flex.Utils.AppUtils;
import com.app.flex.Utils.PrefUtils;
import com.app.flex.model.Result;
import com.app.flex.model.User;
import com.app.flex.retrofit.ApiManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.appevents.UserDataStore.EMAIL;

public class SignInActivity extends AppCompatActivity {

	RelativeLayout mTopRl;
	TextView mRegisterTv;
	Button mSignInBtn;
	EditText mEmailEt, mPasswordEt;
	ProgressDialog pd;
	TextView mForgotPasswordTv;

	CallbackManager callbackManager;

	LoginButton loginButton;
	GoogleSignInClient mGoogleSignInClient;

	ImageView mFacebookIv;
	ImageView mGoogleIv;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_signin);

		callbackManager = CallbackManager.Factory.create();

		loginButton = findViewById(R.id.login_button);
		loginButton.setReadPermissions(Arrays.asList(EMAIL));

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		 mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
		GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

		SignInButton signInButton = findViewById(R.id.sign_in_button);
		signInButton.setSize(SignInButton.SIZE_STANDARD);


		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				Log.d("result", "" + loginResult);
				setFacebookData(loginResult);
			}

			@Override
			public void onCancel() {

			}

			@Override
			public void onError(FacebookException exception) {

			}
		});


		mGoogleIv=findViewById(R.id.google_iv);
		mGoogleIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signIn();
			}
		});

		mFacebookIv = findViewById(R.id.facebook_iv);
		mFacebookIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//loginButton.performClick();
				LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("public_profile", "email", "user_birthday"));
			}
		});

		mForgotPasswordTv = findViewById(R.id.forgot_password_tv);
		mForgotPasswordTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
				startActivity(i);
			}
		});


		mTopRl = findViewById(R.id.top_rl);
		mEmailEt = findViewById(R.id.email_et);
		mPasswordEt = findViewById(R.id.password_et);
		mPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
		pd = new ProgressDialog(this);
		PrefUtils.initPreferance(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				(int) (AppUtils.getDeviceHeight(this) / 2.5f));
		mTopRl.setLayoutParams(params);

		mRegisterTv = findViewById(R.id.register_tv);
		mRegisterTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
				startActivity(i);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish();
			}
		});

		mSignInBtn = findViewById(R.id.sign_in_btn);
		mSignInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mEmailEt.getText().toString().isEmpty()) {
					Toast.makeText(SignInActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
				} else if (mPasswordEt.getText().toString().isEmpty()) {
					Toast.makeText(SignInActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
				} else {
					doLogin(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
				}
			}
		});
	}
	private void signIn() {
		Intent signInIntent = mGoogleSignInClient.getSignInIntent();
		startActivityForResult(signInIntent, 1001);
	}
	private void setFacebookData(LoginResult loginResult) {
		GraphRequest request = GraphRequest.newMeRequest(
				loginResult.getAccessToken(),
				new GraphRequest.GraphJSONObjectCallback() {
					@Override
					public void onCompleted(JSONObject object, GraphResponse response) {
						try {
							Log.i("Response", response.toString());

							String id=response.getJSONObject().getString("id");
							String email = response.getJSONObject().getString("email");
							String firstName = response.getJSONObject().getString("first_name");
							String lastName = response.getJSONObject().getString("last_name");
							String profileURL = "";
							if (Profile.getCurrentProfile() != null) {
								profileURL = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 400, 400).toString();
							}
							Log.d("aa","aa");
						} catch (JSONException e) {
							Toast.makeText(SignInActivity.this, "Erroe", Toast.LENGTH_SHORT).show();
						}
					}
				});
		Bundle parameters = new Bundle();
		parameters.putString("fields", "id,email,first_name,last_name");
		request.setParameters(parameters);
		request.executeAsync();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1001) {
			// The Task returned from this call is always completed, no need to attach
			// a listener.
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			handleSignInResult(task);
		}
	}
	private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
		try {
			GoogleSignInAccount account = completedTask.getResult(ApiException.class);
			Log.w("TAG", "signInResult:failed code=" +account);

			String email=account.getEmail();
			String id=account.getId();
			String firtname=account.getGivenName();
			String lastname=account.getFamilyName();
			//String photo=account.getPhotoUrl().toString();
		} catch (ApiException e) {
			Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
		}
	}
	private void doLogin(String email, String password) {

		pd.show();
		pd.setMessage("Loading");

		ApiManager.getService().login(email, password, "android", "").enqueue(new Callback<Result>() {

			@Override
			public void onResponse(Call<Result> call, Response<Result> response) {

				if (response != null && response.body() != null) {

					Result result = response.body();
					Log.d("login response", result.getStatus());

					if (result.getStatus().equalsIgnoreCase("success")) {
						pd.cancel();

						User user = result.getUser();
						PrefUtils.addUserInfo(user);

						if (user.getmProfileUpdated().equals("1")) {
							if (user.getmRole().equals("1")) {
								Intent i = new Intent(SignInActivity.this, DriverActivity.class);
								startActivity(i);
								finish();
							} else {
								Intent i = new Intent(SignInActivity.this, HomeActivity.class);
								startActivity(i);
								finish();
							}

						} else {
							Intent i = new Intent(SignInActivity.this, CreateProfileActivity.class);
							startActivity(i);
							finish();
						}
					} else {
						pd.cancel();
						Toast.makeText(SignInActivity.this, "Please Enter Correct Username or Password", Toast.LENGTH_SHORT).show();
					}

				} else {
					pd.cancel();
					Toast.makeText(SignInActivity.this, "Please Enter Correct Username or Password", Toast.LENGTH_SHORT).show();
				}


			}

			@Override
			public void onFailure(Call<Result> call, Throwable t) {
				Toast.makeText(SignInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
