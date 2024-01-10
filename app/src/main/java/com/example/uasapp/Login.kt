package com.example.uasapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.uasapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.logTxtReg.setOnClickListener {
            var intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        binding.logBtnLogin.setOnClickListener{
            val email = binding.logEdtEmail.text.toString()
            val password = binding.logEdtPassword.text.toString()

            if(email.isEmpty()){
                binding.logEdtEmail.error = "Email Have to be Filled"
                binding.logEdtEmail.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.logEdtEmail.error = "Email is not Valid"
                binding.logEdtEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.logEdtPassword.error = "Password Have to be Filled"
                binding.logEdtPassword.requestFocus()
                return@setOnClickListener
            }

            if(password.length < 6){
                binding.logEdtPassword.error = "Password Have to be 6 Character Minimum"
                binding.logEdtPassword.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }

    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Welcome $email", Toast.LENGTH_SHORT).show()
                    var pindah = Intent(this,Fragment::class.java)
                    pindah.putExtra("TARGET_FRAGMENT", "fHome")
                    startActivity(pindah)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}