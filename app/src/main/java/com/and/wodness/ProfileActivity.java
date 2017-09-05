package com.and.wodness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.app.Activity;
import android.app.DialogFragment;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import CountryPicker.CountryPicker;
import CountryPicker.CountryPickerListener;
import CountryPicker.Country;

/**
 * Created by GangXian on 8/28/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    String gen;
    boolean isChecked = true;
    private CountryPicker mCountryPicker;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private ImageView ivImage;
    private String userChoosenTask;
    @BindView(R.id.profile_done_button)
    Button profiledonebtn;
    @BindView(R.id.profile_private_check_button)
    CustomButton checkbtn;
    CustomTextView datePic, countryPic, genderPic;
    CustomEditText firstname, lastname, height, weight, username, email;
    ImageView img;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        initialize();
        setListener();
        firstname = (CustomEditText) findViewById(R.id.profile_firstname_edittext);
        lastname = (CustomEditText) findViewById(R.id.profile_lastname_edittext);
        height = (CustomEditText) findViewById(R.id.profile_height_edittext);
        weight = (CustomEditText) findViewById(R.id.profile_weight_edittext);
        username = (CustomEditText) findViewById(R.id.profile_username_edittext);
        email = (CustomEditText) findViewById(R.id.profile_email_edittext);
        datePic = (CustomTextView) findViewById(R.id.profile_ddmmyy_edittext);
        datePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(ProfileActivity.this);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"Date Picker");
            }

        });
        img = (ImageView) findViewById(R.id.profile_photo_imageview);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ProfileActivity.this, Camera_Frag.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                    selectImage();
            }
        });
        genderPic = (CustomTextView) findViewById(R.id.profile_gender_edittext);
        genderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] gender = {"Male","Female"};
                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                alert.setTitle("Select Gender");
                alert.setSingleChoiceItems(gender,-1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(gender[which]=="Male")
                        {
                            genderPic.setText("Male");

                        }
                        else if (gender[which]=="Female")
                        {
                            genderPic.setText("Female");
                            finish();
                        }
                    }
                });
                alert.show();
            }

        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
    }
    private void setListener() {
        mCountryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode,
                                        int flagDrawableResID) {
                countryPic.setText(name);
                mCountryPicker.dismiss();
            }
        });
        countryPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                countryPic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//                    public void onFocusChange(View v, boolean hasFocus) {
//
//                        if(hasFocus){
//                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(countryPic.getWindowToken(), 0);
//                        }
//                    }
//                });

                mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

        getUserCountryInfo();
    }
    @OnClick(R.id.profile_private_check_button)
    public void oncheckbuttonClicked()
    {
        checkbtn.setBackgroundResource(R.drawable.round_shape);
        if(isChecked == true) {
            isChecked = false;
            checkbtn.setBackgroundResource(R.drawable.round_shape);
            Intent intent = new Intent(ProfileActivity.this, ProfileStatusActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(isChecked == false)
        {
            checkbtn.setBackgroundResource(R.drawable.private_profile_check);
            isChecked = true;
        }
    }
    @OnClick(R.id.profile_done_button)
    public void onProfileDoneButtonClicked()
    {
        Intent intent = new Intent(ProfileActivity.this, ProfileModifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("firstname", firstname.getText().toString());
        intent.putExtra("lastname", lastname.getText().toString());
        intent.putExtra("date", datePic.getText().toString());
        intent.putExtra("gender", genderPic.getText().toString());
        intent.putExtra("height", height.getText().toString());
        intent.putExtra("weight", weight.getText().toString());
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("country", countryPic.getText().toString());

        startActivity(intent);
        finish();
    }
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
    private void initialize() {
        countryPic = (CustomTextView) findViewById(R.id.profile_country_edittext);



        mCountryPicker = CountryPicker.newInstance("Select Country");

        // You can limit the displayed countries
        ArrayList<Country> nc = new ArrayList<>();
        for (Country c : Country.getAllCountries()) {
            //if (c.getDialCode().endsWith("0"))
            {
                nc.add(c);
            }
        }
        // and decide, in which order they will be displayed
        //Collections.reverse(nc);
        mCountryPicker.setCountriesList(nc);
    }

    private void getUserCountryInfo() {
        Country country = Country.getCountryFromSIM(getApplicationContext());
        if (country != null) {
            countryPic.setText(country.getName());
        }
    }
}

