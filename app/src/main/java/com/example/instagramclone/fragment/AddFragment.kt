package com.example.instagramclone.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentAddBinding
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.post.AddPost
import com.example.instagramclone.post.AddReels
import com.example.instagramclone.post.AddStory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addPost.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), AddPost::class.java))


        }
        binding.addStory.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), AddStory::class.java))


        }
        binding.addReels.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), AddReels::class.java))


        }
        return binding.root


    }
}

