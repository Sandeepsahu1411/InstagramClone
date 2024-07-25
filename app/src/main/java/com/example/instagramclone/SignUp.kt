package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.example.instagramclone.models.User
import com.example.instagramclone.utils.USER_NODE
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUp : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)

    }
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {

            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it != null) {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val text =
            "<font color=FF000000>Already have an Account </font> <font color=#e21000>Login?</font>"
        binding.loginText.setText(Html.fromHtml(text))
        user = User()

        //profile edit work
        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                binding.registerButton.text = "Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener {

                        user = it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profileImage)
                            binding.username.editText?.setText(user.name)
                            binding.email.editText?.setText(user.email)
                            binding.password.editText?.setText(user.password)
                            binding.loginText.setText(user.name)

                        }
                    }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.registerButton.setOnClickListener {
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                            Toast.makeText(
                                this@SignUp, "Profile Updated", Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@SignUp, HomeActivity::class.java))
                            finish()
                        }
                }
            } else {


                if (binding.username.editText?.text.toString()
                        .equals("") or binding.email.editText?.text.toString()
                        .equals("") or binding.password.editText?.text.toString().equals("")
                ) {
                    Toast.makeText(this@SignUp, "Plese fill All field", Toast.LENGTH_SHORT).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString()
                    ).addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            user.name = binding.username.editText?.text.toString()
                            user.email = binding.email.editText?.text.toString()
                            user.password = binding.password.editText?.text.toString()
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@SignUp, "Login successful", Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this@SignUp, HomeActivity::class.java))
                                    finish()
                                }

                        } else {
                            Toast.makeText(
                                this@SignUp, result.exception?.localizedMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.loginText.setOnClickListener {
            startActivity(Intent(this@SignUp, LoginActivity::class.java))
            finish()
        }
    }
}
