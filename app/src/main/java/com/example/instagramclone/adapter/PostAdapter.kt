package com.example.instagramclone.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.databinding.PostDesignBinding
import com.example.instagramclone.models.Post
import com.example.instagramclone.models.User
import com.example.instagramclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class PostAdapter(var context: Context, var postList: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val post = postList[position]
        val db = FirebaseFirestore.getInstance()

        val uid = post.Uid
        Log.d("PostAdapter", "Fetching user with Uid: $uid")

        if (uid != null && uid.isNotEmpty()) {
            db.collection(USER_NODE).document(uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject<User>()
                    if (user != null) {
                        Glide.with(context).load(user.image).placeholder(R.drawable.profile)
                            .into(holder.binding.profileImage)
                        holder.binding.uname.text = user.name
                    } else {
                        holder.binding.uname.text = "Unknown User"
                    }
                }.addOnFailureListener { e ->
                    Log.e("PostAdapter", "Error fetching user", e)
                    holder.binding.uname.text = "Error loading user"
                }
        } else {
            holder.binding.uname.text = "Invalid Uid"
        }

        Glide.with(context).load(post.postUri).placeholder(R.drawable.loading)
            .into(holder.binding.image)

        try{
            val text=TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text = text
        }catch (e:Exception){

        }
        holder.binding.direct.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"  // Set type as plain text
            intent.putExtra(Intent.EXTRA_TEXT, postList[position].postUri)
            context.startActivity(intent)


        }

        holder.binding.caption.text = post.caption
        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.red_heart)
        }

    }

}
