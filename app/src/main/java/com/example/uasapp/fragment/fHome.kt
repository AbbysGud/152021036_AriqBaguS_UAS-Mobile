package com.example.uasapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uasapp.math.Calculator
import com.example.uasapp.math.Money
import com.example.uasapp.R
import com.example.uasapp.math.BMI

class fHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_f_home, container, false)

        val btnCalculate = view.findViewById<Button>(R.id.home_btn_calculate)
        val btnMoney = view.findViewById<Button>(R.id.home_btn_money)
        val btnBmi = view.findViewById<Button>(R.id.home_btn_bmi)

        btnCalculate.setOnClickListener {
            val intent = Intent(requireActivity(), Calculator::class.java)
            startActivity(intent)
        }

        btnMoney.setOnClickListener {
            val intent = Intent(requireActivity(), Money::class.java)
            startActivity(intent)
        }

        btnBmi.setOnClickListener {
            val intent = Intent(requireActivity(), BMI::class.java)
            startActivity(intent)
        }

        return view
    }

}