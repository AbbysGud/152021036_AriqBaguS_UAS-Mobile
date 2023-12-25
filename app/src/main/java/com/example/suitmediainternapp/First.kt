package com.example.suitmediainternapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import java.util.Locale

class First : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        //region inisiasi semua komponen
        val btn_next = findViewById<Button>(R.id.first_btn_next)
        val btn_palindrom = findViewById<Button>(R.id.first_btn_check)
        val txt_palindrom = findViewById<EditText>(R.id.first_edt_palindrom)
        val txt_nama = findViewById<EditText>(R.id.first_edt_nama)
        //endregion

        //region ketika button check di klik
        btn_palindrom.setOnClickListener {
            val palindrom = txt_palindrom.text.toString()

            //menghilangkan whitespace
            val original = palindrom.replace("\\s".toRegex(), "").lowercase(Locale.getDefault())

            //membalikkan isi string dari original (misal kasur jadi rusak)
            val reversed = original.reversed()

            //menentukan isi pesan pada dialog
            val pesan = if (original.isEmpty()) "Input the palindrom text first"
                        else if (original == reversed) "$palindrom is Palindrome"
                        else "$palindrom is not palindrome"

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder
                .setTitle("Palindrome Check")
                .setMessage(pesan)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

            alertDialogBuilder.create().show()
        }
        //endregion

        //region ketika button next di klik
        btn_next.setOnClickListener {
            val nama = txt_nama.text.toString()

            if(nama.isEmpty()){
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder
                    .setTitle("Warning!!")
                    .setMessage("Please input your name first")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

                alertDialogBuilder.create().show()
            }else{
                txt_nama.setText("")
                txt_palindrom.setText("")
                val pindah = Intent(this, Second::class.java)
                pindah.putExtra("txt_nama", nama)
                startActivity(pindah)
            }
        }
        //endregion
    }
}