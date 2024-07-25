package com.example.instagramclone.post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityAddReelsBinding
import com.example.instagramclone.models.Reels
import com.example.instagramclone.models.User
import com.example.instagramclone.utils.REELS
import com.example.instagramclone.utils.REELS_FOLDER
import com.example.instagramclone.utils.USER_NODE
import com.example.instagramclone.utils.uploadImage
import com.example.instagramclone.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class AddReels : AppCompatActivity() {
   val binding by lazy {
       ActivityAddReelsBinding.inflate(layoutInflater)
   }
    private lateinit var videoUrl:String
    lateinit var progressDialog:ProgressDialog
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.reelPost.setVideoURI(uri)
            binding.reelPost.start()

            uploadVideo(uri, REELS_FOLDER, progressDialog) { url ->
                if (url != null) {

                    videoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        progressDialog=ProgressDialog(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            finish()

        }
        binding.reelPost.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.sharePost.setOnClickListener{
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user:User=it.toObject<User>()!!

            val reels: Reels = Reels(videoUrl!!,binding.caption.text.toString(),user.image!!)
            Firebase.firestore.collection(REELS).document().set(reels).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REELS).document().set(reels)
                    .addOnSuccessListener {
                        Toast.makeText(this@AddReels, "Posted Sucessfull", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddReels,HomeActivity::class.java))
                        finish()
                    }

                        }
                }

        }

        binding.cancel.setOnClickListener {
            startActivity(Intent(this@AddReels, HomeActivity::class.java))
            finish()
        }
    }
}