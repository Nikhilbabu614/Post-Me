package com.example.postme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.postme.daos.Postdao;
import com.example.postme.models.Post;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.firebase.ui.database.FirebaseRecyclerOptions.*;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn ;
    AppCompatImageButton profile;
    PostAdapter ad;
    private Postdao dao;
    private RecyclerView recyclerView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this,CreatePostActivity.class);
            startActivity(intent);
        });

        profile = (AppCompatImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        dao = new  Postdao();
        CollectionReference postcollections = dao.usercollection;
        Query query = postcollections.orderBy("time", Query.Direction.DESCENDING);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirestoreRecyclerOptions<Post> recyclerOptions =  new  FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post.class).build();
        ad = new PostAdapter(recyclerOptions);
        recyclerView.setAdapter(ad);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ad.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ad.stopListening();
    }
}