package com.example.uasapp.math

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.uasapp.Fragment
import com.example.uasapp.R

class BMI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        val tinggi = findViewById<EditText>(R.id.edt_tb)
        val berat = findViewById<EditText>(R.id.edt_bb)

        val calcButton = findViewById<Button>(R.id.btn_calcbmi)
        val clearButton = findViewById<Button>(R.id.btn_clrbmi)
        val bmiInfo = findViewById<TextView>(R.id.txt_hasilbmi)
        val btnBack = findViewById<ImageView>(R.id.bmi_btn_back)

        calcButton.setOnClickListener{
            var nilaiTinggi = 0.0
            var nilaiBerat = 0.0
            if(tinggi.text.toString().isNotEmpty()){
                nilaiTinggi = (tinggi.text.toString().toDouble())/100
            }
            if(berat.text.toString().isNotEmpty()){
                nilaiBerat = berat.text.toString().toDouble()
            }
            if(nilaiBerat > 0.0 && nilaiTinggi > 0.0){
                val bmiValue = String.format(" %.2f", nilaiBerat/(nilaiTinggi*nilaiTinggi))
                val bmiDouble = bmiValue.toDouble()
                bmiInfo.text = "Your BMI ${String.format(" %.2f", bmiDouble)} considered " +
                        "${hasilBMI(nilaiBerat/(nilaiTinggi*nilaiTinggi))}"
            }
            else {
                Toast.makeText(this, "Please Input BW and BH thats > 0", Toast.LENGTH_LONG).show()
            }
        }

        clearButton.setOnClickListener {
            berat.text.clear()
            tinggi.text.clear()
            bmiInfo.text = ("")
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, Fragment::class.java)
            intent.putExtra("TARGET_FRAGMENT", "fHome")
            startActivity(intent)
        }
    }

    private fun hasilBMI(bmi:Double):String{
        lateinit var result: String

        if (bmi < 18.5) {
            result = "Underweight"
        } else if (bmi >= 18.5 && bmi < 24.9) {
            result = "Normal Weight"
        } else if (bmi >= 24.9 && bmi < 30) {
            result = "Overweight"
        } else {
            result = "Obesity"
        }

        return result
    }
}