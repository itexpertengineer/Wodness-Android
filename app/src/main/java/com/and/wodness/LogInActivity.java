package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.and.wodness.api.APIManager;
import com.and.wodness.api.LoginResponse;
import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.and.wodness.model.LoginRequestInfo;
import com.and.wodness.utils.util;

import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by GangXian on 8/25/2017.
 */

public class LogInActivity extends BaseActivity {

    @BindView(R.id.login_forgotpassword_button)
    Button loginforgotpwdbtn;

    @BindView(R.id.login_login_button)
    Button loginloginbtn;

    @BindView(R.id.login_usernameoremail_edittext)
    EditText loginuseroremailedtext;

    @BindView(R.id.login_password_edittext)
    EditText loginpswdedttext;

    WodnessAPIService apiService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginpswdedttext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // If triggered by an enter key, this is the event; otherwise, this is null.
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do whatever you want here
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.login_forgotpassword_button)
    public void onButtonClicked() {
        Intent intent = new Intent(LogInActivity.this, RecoverActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//        finish();
    }

    @OnClick(R.id.login_login_button)
    public void onLoginLoginButtonClicked() {
        if (validateInputs())
            login();

//        Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
    }

    private void login() {
        final String strEmail = loginuseroremailedtext.getText().toString().trim();
        final String strPassword = loginpswdedttext.getText().toString().trim();

        final LoginRequestInfo logininfo = new LoginRequestInfo();

 //       strEmail = "tesst1@test.com";
//        strPassword = "asdf";
        logininfo.setEmail(strEmail);
        logininfo.setPassword(strPassword);

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(new APIManager<LoginResponse>().createGsonConverter(LoginResponse.class))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(WodnessAPIService.class);

        showProgressDialog("", false, null);
        Observable<LoginResponse> call = apiService.login(logininfo);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            String errorMsg = response.message();

                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onNext(LoginResponse response) {

//                        if (response.getStatusCode() != 200) {
//                            hideProgressDialog();
//
//                            return;
//                            Intent resetPasswordIntent = new Intent(LogInActivity.this, RecoverActivity.class);
//                            resetPasswordIntent.putExtra("email", strEmail);
//                            //resetPasswordIntent.putExtra("login_error_msg", response.getMsg());
//                            startActivity(resetPasswordIntent);


                        if (response.getStatusCode() != 200) {

                            hideProgressDialog();
                            Intent resetPasswordIntent = new Intent(LogInActivity.this, RecoverActivity.class);
                            resetPasswordIntent.putExtra("email", strEmail);
                          //resetPasswordIntent.putExtra("login_error_msg", response.getMsg());
                            startActivity(resetPasswordIntent);
                            return;
                        }

                        //if new email/password login , then save account info
//                        if(!strEmail.equalsIgnoreCase(strSavedEmail) || !strPassword.equalsIgnoreCase(strSavedPassword))
//                        {
//                            MySharedPreferenceManager sf = new MySharedPreferenceManager(LoginActivity.this);
//                            sf.setValueString("email" , strEmail);
//                            sf.setValueString("password" , strPassword);
//                            sf.setValueLong("signedTime" , System.currentTimeMillis());
//                        }
                        //MainApplication.getInstance().setUserIdHash(response.getUseridHash());
//                        MainApplication.getInstance().setProductsList(response.getProducts());
                        //MainApplication.getInstance().setCurrentUserInfo(response.getData());
//                        getSettingInfo();

                    }
                });
    }

    private boolean validateInputs() {
        String strEmail = loginuseroremailedtext.getText().toString().trim();
        String strPassword = loginpswdedttext.getText().toString().trim();
        if (strEmail.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_email), Toast.LENGTH_LONG);
            return false;
        }
        if (strPassword.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_password), Toast.LENGTH_LONG);
            return false;
        }

        if (!util.isEmailValid(strEmail)) {
            showToast(getResources().getString(R.string.str_alert_msg_invalid_email), Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }
}
//
//    private void getSettingInfo()
//    {
//        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(AppConstants.API_BASE_URL)
//                .addConverterFactory(new APIManager<SettingResponse>().createGsonConverter(SettingResponse.class))
//                .addCallAdapterFactory(rxAdapter)
//                .build();
//
//        apiService = retrofit.create(WodnessAPIService.class);
//
//        String uid = String.valueOf(MainApplication.getInstance().getCurrentUserInfo().getId());
//
//        Observable<SettingResponse> call = apiService.getSettingInfo(uid);
//        Subscription subscription = call
//                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<SettingResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // cast to retrofit.HttpException to get the response code
//                        if (e instanceof HttpException) {
//                            HttpException exception = (HttpException)e;
//                            Response response = exception.response();
//                            String errorMsg = response.message();
//
//                        }
//                        hideProgressDialog();
//                    }
//
//                    @Override
//                    public void onNext(SettingResponse response) {
//                        //if(validateInputs())
//                        if(response.getStatusCode() != 200)
//                        {
//                            hideProgressDialog();
//                            LogInActivity.this.showErrorMessage("Failed to Login" , response.getMsg());
//
//                            return;
//                        }
//                        MainApplication.getInstance().setCurrentSettingInfo(response.getData());
//                        Intent homeIntent = new Intent(LogInActivity.this, ProfileActivity.class);
//                        startActivity(homeIntent);
//                        hideProgressDialog();
//                        finish();
//                    }
//                });
//    }
//}