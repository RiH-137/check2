package com.example.frienzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import com.example.frienzy.Models.User
import com.example.frienzy.databinding.ActivityLoginBinding
import com.example.frienzy.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


/*
class LoginActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //redirect button
        val button = findViewById<TextView>(R.id.signup1)
        button.setOnClickListener {
                startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
                finish()





                binding.loginbtn.setOnClickListener {
                    val email = binding.email.editText?.text.toString().trim()
                    val password = binding.password.editText?.text.toString().trim()

                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this@LoginActivity, "Please fill in all required information", Toast.LENGTH_SHORT).show()
                    } else {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val user = User( email, password) // Create a User object

                                    db.collection("USER_NODE")
                                        .document(auth.currentUser?.uid ?: "")
                                        .set(user)
                                        .addOnSuccessListener {
                                            Toast.makeText(this@LoginActivity, "Login...", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))        //redirect to home page
                                            finish()

                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this@LoginActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                        }
                                } else {
                                    Toast.makeText(this@LoginActivity, "Error: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }


                }



        }

    }
}

*/

class LoginActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize the binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a click listener for the "Sign Up" button
        val button = findViewById<TextView>(R.id.signup1)
        button.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }

        // Set up a click listener for the "Login" button
        binding.loginbtn.setOnClickListener {
            val email = binding.email.editText?.text.toString().trim()
            val password = binding.password.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please fill in all required information", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Successfully logged in
                            Toast.makeText(this, "Login Successful...", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            // Login failed
                            Toast.makeText(this@LoginActivity, "Error: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
