package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;

/**
 * Created by GangXian on 9/4/2017.
 */

public class ProfileModifyActivity extends AppCompatActivity {
    CustomEditText firstname, lastname, height, weight, username, email;
    CustomTextView datePic, genderPic, countryPic;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_modify);
        ButterKnife.bind(this);
        btn = (CustomButton) findViewById(R.id.profile_save_button);

        firstname = (CustomEditText) findViewById(R.id.profile_firstname_edittext);
        lastname = (CustomEditText) findViewById(R.id.profile_lastname_edittext);
        height = (CustomEditText) findViewById(R.id.profile_height_edittext);
        weight = (CustomEditText) findViewById(R.id.profile_weight_edittext);
        username = (CustomEditText) findViewById(R.id.profile_username_edittext);
        email = (CustomEditText) findViewById(R.id.profile_email_edittext);
        datePic = (CustomTextView) findViewById(R.id.profile_ddmmyy_edittext);
        genderPic = (CustomTextView) findViewById(R.id.profile_gender_edittext);
        countryPic = (CustomTextView) findViewById(R.id.profile_country_edittext);
        Intent intent = this.getIntent();
        String sfirstname = intent.getStringExtra("firstname");
        String slastname = intent.getStringExtra("lastname");
        String sdate = intent.getStringExtra("date");
        String sgender = intent.getStringExtra("gender");
        String sheight = intent.getStringExtra("height");
        String sweight = intent.getStringExtra("weight");
        String susername = intent.getStringExtra("username");
        String semail = intent.getStringExtra("email");
        String scountry = intent.getStringExtra("country");
        firstname.setText(sfirstname);
        lastname.setText(slastname);
        height.setText(sheight + " "+"CM");
        datePic.setText(sdate);
        genderPic.setText(sgender);
        weight.setText(sweight + " " + "KG");
        username.setText(susername);
        email.setText(semail);
        countryPic.setText(scountry);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileModifyActivity.this, ProfileStatusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
