package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.databinding.StoryRvDesignBinding
import com.example.instagramclone.models.User

class StoryRvAdapter (var context: Context,var storyList:ArrayList<User>): RecyclerView.Adapter<StoryRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:StoryRvDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var binding=StoryRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(storyList.get(position).image).placeholder(R.drawable.profile).into(holder.binding.storyImage)
        holder.binding.storyUname.text=storyList.get(position).name
    }
}