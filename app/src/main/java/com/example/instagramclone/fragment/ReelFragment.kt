package com.example.instagramclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.R
import com.example.instagramclone.adapter.ReelsAdapter
import com.example.instagramclone.databinding.FragmentReelBinding
import com.example.instagramclone.models.Reels
import com.example.instagramclone.utils.REELS
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelFragment : Fragment() {

    private lateinit var binding: FragmentReelBinding
    lateinit var adapter: ReelsAdapter
    var reelsList = ArrayList<Reels>()


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
        binding = FragmentReelBinding.inflate(inflater, container, false)
        adapter = ReelsAdapter(requireContext(), reelsList)
        binding.reelsViewPager.adapter = adapter

        Firebase.firestore.collection(REELS).get().addOnSuccessListener {
            var tempList = ArrayList<Reels>()
            for (i in it.documents) {
                var reels = i.toObject<Reels>()!!
                tempList.add(reels)
            }
            reelsList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }


}