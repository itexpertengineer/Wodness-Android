package com.and.wodness;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.and.wodness.app.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.id.content;
import static com.and.wodness.R.id.textView;

/**
 * Created by GangXian on 8/25/2017.
 */

public class JoinUsActivity extends AppCompatActivity {
    String str1 = "B Y   S I G N I N G   U P   T O";
    String str2 = "W O D N E S S,   Y O U   A G R E E   T O   O U R";
    String str3 = "P R I V A C Y   P O L I C Y";
    @BindView(R.id.joinus_login_button)
    Button joinus_login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinus);
        ButterKnife.bind(this);
        TextView textView1 = (CustomTextView) findViewById(R.id.htmlstring1);
        TextView textView2 = (CustomTextView) findViewById(R.id.htmlstring2);

        SpannableString ss1=  new SpannableString(str1);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0 , 1 , 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 1, 0);// set color
        textView1.setText(ss1);

        SpannableString ss2=  new SpannableString(str2);
        ss2.setSpan(new RelativeSizeSpan(1.5f), 0 , 1 , 0); // set size
        ss2.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 1, 0);// set color
        textView2.setText(ss2);

        Button button=(CustomButton)findViewById(R.id.joinus_privat);
        SpannableString ss3=  new SpannableString(str3);
        ss3.setSpan(new RelativeSizeSpan(1.5f), 0 , 1 , 0);
        ss3.setSpan(new RelativeSizeSpan(1.5f), 16, 17, 0);
        ss3.setSpan(new UnderlineSpan(), 0, ss3.length(),0);// set size
        ss3.setSpan(new ForegroundColorSpan(Color.argb(255,24,198,255)), 0, 1, 0);// set color
        button.setText(ss3);


    }

    @OnClick(R.id.joinus_login_button)
    public void onJoinusLoginButtonClicked()
    {
        Intent intent = new Intent(JoinUsActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//        finish();
    }
    @OnClick(R.id.joinus_privat)
    public void onJoinusprivatClicked()
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(AppConstants.GOOGLE_URL));
        startActivity(i);
//        finish();
    }
    @OnClick(R.id.joinus_creat_account_button)
    public void onJoinusCreateAccountButtonClicked()
    {
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(AppConstants.LOGIN_URL));
//        startActivity(i);
        Toast.makeText(JoinUsActivity.this, "A New Account Created.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(JoinUsActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}