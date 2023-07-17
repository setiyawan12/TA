package com.zahro.pneumonia.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zahro.pneumonia.R
import com.zahro.pneumonia.model.Article
import com.zahro.pneumonia.ui.DetailArticleActivity

class ArticleAdapter (var activity:Activity, var data:List<Article>):RecyclerView.Adapter<ArticleAdapter.Holder>(){
    class Holder (view:View):RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.tv_title)
        val author =view.findViewById<TextView>(R.id.tv_author)
        val layout = view.findViewById<View>(R.id.layout)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_article,parent,false)
        return  Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = data[position].title
        holder.author.text=data[position].aktor
        holder.layout.setOnClickListener {
            val start = Intent(activity,DetailArticleActivity::class.java)
            val str = Gson().toJson(data[position],Article::class.java)
            start.putExtra("extra",str)
            activity.startActivity(start)
        }
    }
    override fun getItemCount(): Int {
        return  data.size
    }
}