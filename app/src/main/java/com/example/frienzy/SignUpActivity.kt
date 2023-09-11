/*
package com.example.frienzy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frienzy.databinding.ActivityMainBinding
import com.example.frienzy.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.example.frienzy.Models.User


class SignUpActivity : AppCompatActivity() {

    //binding
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user:User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //binding.root => passing root element

        user=User()



        binding.signupBtn.setOnClickListener(){
            if(binding.name.editText?.text.toString().equals(" ") or
                binding.email.editText?.text.toString().equals(" ") or
                binding.password.editText?.text.toString().equals(" ")
                    )
            {
                Toast.makeText(this@SignUpActivity, "Please fill the require information", Toast.LENGTH_SHORT).show()

            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener {
                    result ->
                    if (result.isSuccessful){
                        user.name=binding.name.editText?.text.toString()
                        user.email=binding.email.editText?.text.toString()
                        user.password=binding.password.editText?.text.toString()
                        Firebase.firestore.collection("User")
                            .document(Firebase.auth.currentUser !! uid).set(user)
                            .addOnSuccessListener{


                                Toast.makeText(this@SignUpActivity, "Signing Up", Toast.LENGTH_SHORT).show()
                                Toast.makeText(this@SignUpActivity, "SignUp Successful", Toast.LENGTH_SHORT).show()

                            }

                    }else{
                        Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }

}
*/


package com.example.frienzy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.frienzy.Models.User
import com.example.frienzy.databinding.ActivitySignUpBinding
import com.example.frienzy.utils.USER_PROFILE_FOLDER
import com.example.frienzy.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Check if the uri is not null
        if (uri != null) {
            // Upload the image to the `USER_PROFILE_FOLDER` folder
            uploadImage(uri, USER_PROFILE_FOLDER){

            if(it ==null){

            }else{

                binding.addImage.setImageURI(uri)

            }}
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            val name = binding.name.editText?.text.toString().trim()
            val email = binding.email.editText?.text.toString().trim()
            val password = binding.password.editText?.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Please fill in all required information", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = User(name, email, password) // Create a User object

                            db.collection("USER_NODE")
                                .document(auth.currentUser?.uid ?: "")
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(this@SignUpActivity, "Successful...", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))        //redirect to home page
                                    finish()

                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this@SignUpActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this@SignUpActivity, "Error: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        //opening gallery
        binding.addImage.setOnClickListener {                                 //addImage-->button name
            // Launch the image picker
            launcher.launch("image/*")
        }

        //login page
        binding.login.setOnClickListener{
            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
            finish()
        }

    }
}