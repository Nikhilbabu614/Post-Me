package com.example.postme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.postme.daos.Postdao;
import com.example.postme.models.Post;

public class CreatePostActivity extends AppCompatActivity {

    EditText text;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        text = findViewById(R.id.editTextTextPersonName);
        submit = findViewById(R.id.button);

        submit.setOnClickListener(v -> {
            String input = text.getText().toString();
                Postdao dao = new Postdao();
                dao.addPost(input);
                finish();
        });
    }
}