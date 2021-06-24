package com.example.postme;
import android.location.GnssAntennaInfo;
import android.os.Build;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.postme.models.Post;
import com.firebase.ui.auth.viewmodel.AuthViewModelBase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import java.util.Objects;

import static com.example.postme.R.drawable.ic_liked;
import static com.example.postme.R.drawable.ic_unliked;

class PostAdapter  extends FirestoreRecyclerAdapter<Post,PostAdapter.PostViewHolder>{

     FirebaseAuth auth = FirebaseAuth.getInstance();
     FirebaseFirestore db = FirebaseFirestore.getInstance();
     CollectionReference usercollection = db.collection("posts");

     public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
        super(options);

    }

     class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView username,timestamp , caption ,likes;
        AppCompatImageButton likebutton;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            username = itemView.findViewById(R.id.username);
            timestamp = itemView.findViewById(R.id.timestamp);
            caption = itemView.findViewById(R.id.caption);
            likebutton = itemView.findViewById(R.id.likebutton);
            likes = itemView.findViewById(R.id.likes);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        PostViewHolder viewHolder = new PostAdapter.PostViewHolder(view);

        viewHolder.likebutton.setOnClickListener(v -> {
            String Postid = getSnapshots().getSnapshot(viewHolder.getAdapterPosition()).getId();
            String user = auth.getCurrentUser().getUid();
            Task<DocumentSnapshot> post = usercollection.document(Postid).get();
            post.addOnCompleteListener(task -> {
                Post snapshot = task.getResult().toObject(Post.class);
                boolean isliked = snapshot.like.contains(user);
                if(isliked){
                    snapshot.like.remove(user);
                }else{
                    snapshot.like.add(user);
                }
                usercollection.document(Postid).set(snapshot);
            });
        });
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(PostViewHolder holder, int position,@Nullable Post model) {
        Glide.with(holder.avatar.getContext()).load(model.user.image).circleCrop().into(holder.avatar);
        holder.username.setText(model.user.name);
        holder.timestamp.setText(Utils.getTimeAgo(model.time));
        holder.caption.setText(model.text);
        holder.likes.setText(""+model.like.size());

        String user = auth.getCurrentUser().getUid();
        boolean isliked = model.like.contains(user);
        if(isliked) holder.likebutton.setImageDrawable(ContextCompat.getDrawable(holder.likebutton.getContext(), ic_liked));
        else{
            if(isliked) holder.likebutton.setImageDrawable(ContextCompat.getDrawable(holder.likebutton.getContext(), ic_unliked));
        }
    }
}