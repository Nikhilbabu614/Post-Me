package com.example.postme.daos;

import com.example.postme.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Userdao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usercollection = db.collection("users");

     public void adduser(User user){
        if(user!=null){
            usercollection.document(user.id).set(user);
        }
    }

    public Task<DocumentSnapshot> getuser(String uid){
         return usercollection.document(uid).get();
    }

}
