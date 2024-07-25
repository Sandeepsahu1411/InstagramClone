package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagramclone.databinding.MyReelsRvDesignBinding
import com.example.instagramclone.models.Reels

class MyReelsRvAdapter(var context: Context, var reelsList: ArrayList<Reels>) :
    RecyclerView.Adapter<MyReelsRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MyReelsRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MyReelsRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(reelsList.get(position).reelsUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postReels)
    }
}