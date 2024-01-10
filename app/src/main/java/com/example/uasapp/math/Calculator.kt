package com.example.uasapp.math

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.example.uasapp.Fragment
import com.example.uasapp.R

class Calculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        //region Inisialisasi button
        var pilihan = 0

        val gEdt_bil1 = findViewById<EditText>(R.id.edt_bil1)
        val gEdt_bil2 = findViewById<EditText>(R.id.edt_bil2)
        val gSpn_operator = findViewById<Spinner>(R.id.spn1)
        val gBtn_calculate = findViewById<Button>(R.id.btn_calculate)
        val gTxt_hasil = findViewById<TextView>(R.id.txt_hasil)
        val btnBack = findViewById<ImageView>(R.id.calculate_btn_back)
        //endregion

        //region tombol hitung
        gBtn_calculate.setOnClickListener {
            var bilangan1 = gEdt_bil1.text.toString().toDouble()
            var bilangan2 = gEdt_bil2.text.toString().toDouble()
            var hasil = 0.0

            if(pilihan.equals(0)){
                hasil = bilangan1 + bilangan2
            } else if (pilihan.equals(1)){
                hasil = bilangan1 - bilangan2
            } else if (pilihan.equals(2)){
                hasil = bilangan1 / bilangan2
            } else if (pilihan.equals(3)){
                hasil = bilangan1 * bilangan2
            }

            gTxt_hasil.text = hasil.toString()
        }
        //endregion

        //region spinner
        gSpn_operator.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("ariq", "item terpilih: " + position)
                    pilihan = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            })
        //endregion

        btnBack.setOnClickListener {
            val intent = Intent(this, Fragment::class.java)
            intent.putExtra("TARGET_FRAGMENT", "fHome")
            startActivity(intent)
        }
    }
}