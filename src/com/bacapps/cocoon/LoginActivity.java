package com.bacapps.cocoon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bacapps.coccon.api.ApiClient;
import com.loopj.android.http.*;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends MotherActivity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.bacappps.cocoon.authentication.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */


	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mUsernameView;
	private Button mSigninView;
	private Button mNextView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private TextView mSignupStateView;
	private Activity loginActivity;
	private boolean signupState = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		loginActivity = this;
		// Set up the login form.
		mUsernameView = (EditText)findViewById(R.id.username);
		mNextView = (Button)findViewById(R.id.next);
		
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		mSigninView = (Button) findViewById(R.id.sign_in_button);
		mSigninView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			  attemptLogin();
			}
		});
		
		mUsernameView.setVisibility(View.GONE);
		mNextView.setVisibility(View.GONE);
		mNextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateUsernameViaAPI(mUsernameView.getText().toString());				
			}
		});
		
		mSignupStateView = (TextView)findViewById(R.id.action_switch_signin_signup);
		mSignupStateView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (signupState){
				  mSignupStateView.setText(R.string.prompt_have_account);
				  signupState = false;
				}else {
				  mSignupStateView.setText(R.string.prompt_not_signin);
				  signupState = true;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			
			
			showProgress(true);
			if (signupState) {
			  userSignupViaAPI(mEmail, mPassword);
			}else {
			  userLoginViaAPI(mEmail, mPassword);
			}
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	public void userLoginViaAPI(String username, String password){
		System.out.println("username = "+ username);
		System.out.println("password = "+ password);
		RequestParams params = new RequestParams("user[username]", username);
		params.put("user[password]", password);
		ApiClient.post("auth/login.json", params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int code, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(code, response);
				try {
					saveSharePref("auth_token", response.getJSONObject("user").getString("auth_token"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				System.out.println("SUCESS signup");
				launchGiftListingsActivity(loginActivity);
			}
			
			@Override
			public void onFailure(Throwable err, String response) {
				// TODO Auto-generated method stub
				super.onFailure(err, response);
				mPasswordView
				.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
				System.out.println("Error = "+err.getMessage());
				System.out.println("Faiuled in");
			}
		});
	}
	
	public void userSignupViaAPI(String email, String password){
		System.out.println("email = "+ email);
		System.out.println("password = "+ password);
		RequestParams params = new RequestParams("user[email]", email);
		params.put("user[username]", email);
		params.put("user[password]", password);
		ApiClient.post("users.json", params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int code, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(code, response);
				try {
					saveSharePref("auth_token", response.getString("auth_token"));
					saveSharePref("user_id", response.getString("id"));
					saveSharePref("user_email", response.getString("email"));
					saveSharePref("user_name", response.getString("username"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				System.out.println("SUCESS signup ="+response.toString());
				mPasswordView.setVisibility(View.GONE);
				mEmailView.setVisibility(View.GONE);
				mUsernameView.setVisibility(View.VISIBLE);
				mNextView.setVisibility(View.VISIBLE);
				m
			}
			
			@Override
			public void onFailure(Throwable err, String response) {
				// TODO Auto-generated method stub
				super.onFailure(err, response);
				mPasswordView
				.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
				System.out.println("Error = "+err.getMessage());
				System.out.println("Faiuled in");
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				showProgress(false);
			}
		});
	}

	
	public void updateUsernameViaAPI(String username){
		RequestParams params = new RequestParams("user[username]", username);
		ApiClient.put("users/"+getSharePref("user_id")+".json", params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int code, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(code, response);
				try {
					saveSharePref("user_name", response.get("user_name").toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				launchGiftListingsActivity(loginActivity);
			}
			
			public void onFailure(Throwable err, JSONObject response) {
				mUsernameView.setError(getString(R.string.error_username_taken));
				mUsernameView.requestFocus();
			};
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				showProgress(false);
			}
		});
	}
	
}
