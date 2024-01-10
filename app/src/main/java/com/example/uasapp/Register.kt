package com.example.uasapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.uasapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.regTxtLogin.setOnClickListener {
            var intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

        binding.regBtnRegister.setOnClickListener {
            val email = binding.regEdtEmail.text.toString()
            val password = binding.regEdtPassword.text.toString()

            if(email.isEmpty()){
                binding.regEdtEmail.error = "Email Have to be Filled"
                binding.regEdtEmail.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.regEdtEmail.error = "Email is not Valid"
                binding.regEdtEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.regEdtPassword.error = "Password Have to be Filled"
                binding.regEdtPassword.requestFocus()
                return@setOnClickListener
            }

            if(password.length < 6){
                binding.regEdtPassword.error = "Password Have to be 6 Character Minimum"
                binding.regEdtPassword.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email, password)
        }

    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Register Successfull", Toast.LENGTH_SHORT).show()
                    val pindah = Intent(this,Login::class.java)
                    startActivity(pindah)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}