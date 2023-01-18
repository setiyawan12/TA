package com.zahro.pneumonia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zahro.pneumonia.data.DetectionResult
import com.zahro.pneumonia.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        extras?.let {
            val result = extras.getParcelable<DetectionResult>(EXTRA_DATA)
            if (result != null){
                populateItem(result)
            }
        }
    }
    private fun populateItem(result: DetectionResult){
        with(binding){
            tvResult.text = result.title
        }
    }
}