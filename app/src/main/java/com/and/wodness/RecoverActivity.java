package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by GangXian on 8/28/2017.
 */

public class RecoverActivity extends AppCompatActivity {
    @BindView(R.id.recover_reset_password_button)
    Button joinus_login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.recover_reset_password_button)
    public void onRecoverResetPasswordButtonClicked()
    {
        Intent intent = new Intent(RecoverActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//        finish();
    }
    @OnClick(R.id.recover_back_button)
    public void onRecoverBackButtonClicked()
    {
        Intent intent = new Intent(RecoverActivity.this, FirstLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//        finish();
    }
//
//    @OnClick(R.id.joinus_login_button)
//    public void onJoinusLoginButtonClicked()
//    {
//        Intent intent = new Intent(JoinUsActivity.this, LogInActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//    }
}
