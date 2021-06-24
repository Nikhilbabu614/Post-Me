package com.example.postme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    Button logout;
    ImageView image;
    TextView text ,email;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout = findViewById(R.id.logout);
        image = findViewById(R.id.pp);
        email = findViewById(R.id.textView2);
        text = findViewById(R.id.pname);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user!=null) {
            Glide.with(this).load(user.getPhotoUrl()).circleCrop().into(image);
            text.setText(user.getDisplayName());
            email.setText(user.getEmail());
        }else{
            text.setText("");
        }

        logout.setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(this,SigninActivity.class);
            startActivity(intent);
        });
    }


}