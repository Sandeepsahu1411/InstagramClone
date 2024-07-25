package com.example.instagramclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.adapter.MyReelsRvAdapter
import com.example.instagramclone.databinding.FragmentMyReelsBinding
import com.example.instagramclone.models.Reels
import com.example.instagramclone.utils.REELS
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyReelsFragment : Fragment() {
    private lateinit var binding: FragmentMyReelsBinding

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
        binding = FragmentMyReelsBinding.inflate(inflater, container, false)

        var reelsList=ArrayList<Reels>()
        var adapter= MyReelsRvAdapter(requireContext(),reelsList)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REELS).get().addOnSuccessListener {
            var tempList= arrayListOf<Reels>()
            for (i in it.documents){
                var reels: Reels =i.toObject<Reels>()!!
                tempList.add(reels)
            }
            reelsList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

}