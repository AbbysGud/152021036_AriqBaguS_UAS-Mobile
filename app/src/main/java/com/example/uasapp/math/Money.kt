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

class Money : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)

        var pilihan1 = 0
        var pilihan2 = 0

        val gEdt_bil1 = findViewById<EditText>(R.id.edt_bil1)
        val gSpn1 = findViewById<Spinner>(R.id.spn1)
        val gSpn2 = findViewById<Spinner>(R.id.spn2)
        val gBtn_convertion = findViewById<Button>(R.id.btn_convertion)
        val gTxt_hasil = findViewById<TextView>(R.id.txt_hasil)
        val clearButton = findViewById<Button>(R.id.btn_clrconvert)
        val btnBack = findViewById<ImageView>(R.id.money_btn_back)
        //endregion

        //region tombol hitung
        gBtn_convertion.setOnClickListener {
            var bilangan = gEdt_bil1.text.toString().toDouble()
            var hasil = 0.0

            if(pilihan1.equals(0)){
                if(pilihan2.equals(0)){
                    hasil = bilangan
                } else if (pilihan2.equals(1)) {
                    hasil = bilangan * 0.000064
                } else if (pilihan2.equals(2)) {
                    hasil = bilangan * 0.000060
                } else if (pilihan2.equals(3)) {
                    hasil = bilangan * 0.0096
                }
            } else if (pilihan1.equals(1)){
                if(pilihan2.equals(0)){
                    hasil = bilangan * 15643.00
                } else if (pilihan2.equals(1)) {
                    hasil = bilangan
                } else if (pilihan2.equals(2)) {
                    hasil = bilangan * 0.94
                } else if (pilihan2.equals(3)) {
                    hasil = bilangan * 150.54
                }
            } else if (pilihan1.equals(2)){
                if(pilihan2.equals(0)){
                    hasil = bilangan * 16706.80
                } else if (pilihan2.equals(1)) {
                    hasil = bilangan * 1.07
                } else if (pilihan2.equals(2)) {
                    hasil = bilangan
                } else if (pilihan2.equals(3)) {
                    hasil = bilangan * 160.74
                }
            } else if (pilihan1.equals(3)){
                if(pilihan2.equals(0)){
                    hasil = bilangan * 104.00
                } else if (pilihan2.equals(1)) {
                    hasil = bilangan * 0.0066
                } else if (pilihan2.equals(2)) {
                    hasil = bilangan * 0.0062
                } else if (pilihan2.equals(3)) {
                    hasil = bilangan
                }
            }

            gTxt_hasil.text = hasil.toString()
        }
        //endregion

        //region spinner 1
        gSpn1.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("ariq", "item terpilih: " + position)
                    pilihan1 = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            })
        //endregion

        //region spinner 2
        gSpn2.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("ariq", "item terpilih: " + position)
                    pilihan2 = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            })
        //endregion

        clearButton.setOnClickListener {
            gEdt_bil1.text.clear()
            gTxt_hasil.text = ("")
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, Fragment::class.java)
            intent.putExtra("TARGET_FRAGMENT", "fHome")
            startActivity(intent)
        }

    }
}