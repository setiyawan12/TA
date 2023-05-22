package com.zahro.pneumonia.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zahro.pneumonia.R
import com.zahro.pneumonia.databinding.ActivityDetailNewsBinding
import com.zahro.pneumonia.model.News
import kotlinx.android.synthetic.main.activity_detail_news.*

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding :ActivityDetailNewsBinding
    lateinit var news:News
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setvalue()
        readmore()
    }

    private fun readmore() {
        binding.btnReadmore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(news.link)
            startActivity(intent)
        }
    }

    private fun setvalue() {
        val data = intent.getStringExtra("extra")
        news = Gson().fromJson(data,News::class.java)
        val image = news.image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(binding.ImageDetail)
        binding.apply {
            TitleNews.text = news.title
            tv_description.text = news.description
        }
    }
}