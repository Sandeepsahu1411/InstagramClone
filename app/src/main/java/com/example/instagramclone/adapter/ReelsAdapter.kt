package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ReelsDesignBinding
import com.example.instagramclone.models.Reels
import com.example.instagramclone.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ReelsAdapter(var context: Context,var reelsList:ArrayList<Reels>) :RecyclerView.Adapter<ReelsAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: ReelsDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=ReelsDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelsList.get(position).profileLink).placeholder(R.drawable.profile).into(holder.binding.profile)
        holder.binding.caption.setText(reelsList.get(position).caption)
        holder.binding.videoView.setVideoPath(reelsList.get(position).reelsUri)
        holder.binding.videoView.setOnPreparedListener{
            holder.binding.progressBar.visibility=View.GONE
            holder.binding.videoView.start()



        }

    }

}