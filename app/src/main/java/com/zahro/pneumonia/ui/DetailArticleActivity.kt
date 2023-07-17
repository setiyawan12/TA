package com.zahro.pneumonia.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.zahro.pneumonia.R
import com.zahro.pneumonia.databinding.ActivityDetailArticleBinding
import com.zahro.pneumonia.model.Article
import kotlinx.android.synthetic.main.activity_detail_article.*
import kotlinx.android.synthetic.main.activity_detail_article.tv_title
import kotlinx.android.synthetic.main.list_item_article.*

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    lateinit var article: Article
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setvalues()
        back()
        readmore()
    }
    fun back(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
    private fun readmore(){
        binding.btnReadmore.setOnClickListener {
            val  intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(article.link)
            startActivity(intent)
        }
    }
    private fun setvalues() {
        val data = intent.getStringExtra("extra")
        article = Gson().fromJson(data,Article::class.java)
        binding.apply {
            tv_title.text = article.title
            tv_description.text = article.description
            tv_nama_author.text =article.aktor
            tv_publish.text = article.publish
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}