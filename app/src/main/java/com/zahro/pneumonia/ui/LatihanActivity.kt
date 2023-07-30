package com.zahro.pneumonia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zahro.pneumonia.R
import com.zahro.pneumonia.databinding.ActivityLatihanBinding

class LatihanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLatihanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLatihanBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        alert()
        checkForm()
    }

    private fun checkForm() {
        val editText = binding.et.text.toString()
        binding.btncek.setOnClickListener {
            Toast.makeText(this, editText.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun alert() {
        binding.btn.setOnClickListener {
            Toast.makeText(this, "button telah di tekan", Toast.LENGTH_SHORT).show()
        }
    }

}