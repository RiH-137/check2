package com.example.frienzy.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frienzy.R
import com.example.frienzy.databinding.FragmentProfileBinding
import com.example.frienzy.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase




@Suppress("UNREACHABLE_CODE")
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding                                //initializing binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user: User? = it.toObject<User>()
                if (user != null) {
                    binding.name.text = user.name
                    binding.bio.text = user.email

                } else {
                    // Handle the case where user data is not available
                }
            }
    }

}