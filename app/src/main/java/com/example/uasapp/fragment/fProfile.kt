package com.example.uasapp.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.uasapp.Login
import com.example.uasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class fProfile : Fragment() {

    lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f_profile, container, false)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user != null){
            view.findViewById<TextView>(R.id.profile_email).text = user.email
        }

        val btnLogout = view.findViewById<Button>(R.id.profile_btn)
        btnLogout.setOnClickListener {
            logout()
            Toast.makeText(requireActivity(), "Logout Successfull!!", Toast.LENGTH_LONG).show()
        }

        return view
    }

    private fun logout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(requireActivity(), Login::class.java)
        startActivity(intent)
        activity?.finish()
    }

}