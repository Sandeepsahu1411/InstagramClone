package com.example.instagramclone.post

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
import com.example.instagramclone.databinding.ActivityAddPostBinding
import com.example.instagramclone.databinding.FragmentAddBinding
import com.example.instagramclone.fragment.AddFragment
import com.example.instagramclone.models.Post
import com.example.instagramclone.models.User
import com.example.instagramclone.utils.POST
import com.example.instagramclone.utils.POST_FOLDER
import com.example.instagramclone.utils.USER_NODE
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class AddPost : AppCompatActivity() {
    val binding by lazy {
        ActivityAddPostBinding.inflate(layoutInflater)
    }
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {

            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.imagePost.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
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
        binding.imagePost.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.cancel.setOnClickListener {
            startActivity(Intent(this@AddPost, HomeActivity::class.java))
            finish()
        }
        binding.sharePost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {

                    var user = it.toObject<User>()!!
                    val post: Post= Post(
                        postUri=  imageUrl!!,
                        caption = binding.caption.text.toString(),
                        Uid =  Firebase.auth.currentUser!!.uid,
                        time=System.currentTimeMillis().toString()
                    )
                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                            .set(post)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@AddPost,
                                    "Posted Sucessfull",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this@AddPost, HomeActivity::class.java))
                                finish()
                            }

                    }
                }
        }
    }
}