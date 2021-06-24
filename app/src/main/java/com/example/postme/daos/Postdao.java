package com.example.postme.daos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.postme.models.Post;
import com.example.postme.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Postdao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference usercollection = db.collection("posts");
    FirebaseAuth auth;

    public  void addPost(String input){
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            String currentuserid = auth.getCurrentUser().getUid();

            Userdao dao = new Userdao();
            Task<DocumentSnapshot> val = dao.getuser(currentuserid);

            val.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                       DocumentSnapshot snapshot = task.getResult();
                       User user = snapshot.toObject(User.class);
                        long time = System.currentTimeMillis();

                        ArrayList<String> arr = new ArrayList<>();
                        Post post = new Post(input,user,time,arr);
                        usercollection.document().set(post);
                    }
                }
            });



        }
    }
}
