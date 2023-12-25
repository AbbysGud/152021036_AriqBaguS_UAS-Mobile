package com.example.suitmediainternapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //region inisisasi semua objek
        val btn_back = findViewById<ImageView>(R.id.second_btn_back)
        val btn_choose = findViewById<Button>(R.id.second_btn_choose)
        val txt_nama1 = findViewById<TextView>(R.id.second_txt_name1)
        val txt_nama2 = findViewById<TextView>(R.id.second_txt_name2)
        //endregion

        //region mengambil data dari halaman lain dan ditampilkan pada TextView
        val nama_1 = intent.getStringExtra("txt_nama")
        val nama_2 = intent.getStringExtra("txt_nama2")

        txt_nama1.text = nama_1
        txt_nama2.text = nama_2
        //endregion

        //region ketika tombol back diklik (tidak menambah intent karena dianggap logout)
        btn_back.setOnClickListener {
            val pindah = Intent(this, First::class.java)
            startActivity(pindah)
        }
        //endregion

        //region ketika tombol choose diklik
        btn_choose.setOnClickListener {
            val pindah = Intent(this, Third::class.java)
            pindah.putExtra("txt_nama", nama_1)
            pindah.putExtra("txt_nama2", nama_2)
            startActivity(pindah)
        }
        //endregion
    }
}