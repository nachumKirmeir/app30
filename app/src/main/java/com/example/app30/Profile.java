package com.example.app30;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sp;//will save the changes in the user profile for next times
    EditText etFirstName, etLastName, etEmail;
    Button btnSave, btnTakePicture, btnReturnHomePage;
    ImageView ivPicture;

    Bitmap bitmap;//for the picture
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getSharedPreferences("profile", 0);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        ivPicture = findViewById(R.id.ivPicture);
        btnSave = findViewById(R.id.btnSave);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnReturnHomePage = findViewById(R.id.btnReturnHomePage);
        btnReturnHomePage.setOnClickListener(this);
        btnTakePicture.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        String firstName = sp.getString("firstName", null);
        String lastName = sp.getString("lastName", null);
        String email = sp.getString("email", null);
        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etEmail.setText(email);


    }

    @Override
    public void onClick(View v) {
        if(v == btnTakePicture){//Intent to take a picture for profile
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 12);
        }
        else if(v == btnSave){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("firstName", etFirstName.getText().toString());
            editor.putString("lastName", etLastName.getText().toString());
            editor.putString("email", etEmail.getText().toString());
            editor.commit();
            ivPicture.setImageBitmap(bitmap);
        }
        else if(v == btnReturnHomePage){
            Intent intent = new Intent(Profile.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 12){
            if(resultCode == RESULT_OK){
                bitmap = (Bitmap) data.getExtras().get("data");
                ivPicture.setImageBitmap(bitmap);
            }
            else{
                Toast.makeText(this, "Something Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}