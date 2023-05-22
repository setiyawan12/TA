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
import com.zahro.pneumonia.model.News
import com.zahro.pneumonia.ui.DetailNewsActivity

class NewsAdapter(var activity: Activity, var data:List<News>):RecyclerView.Adapter<NewsAdapter.Holder>() {
    class Holder(view:View):RecyclerView.ViewHolder(view){
        val ImageNews = view.findViewById<ImageView>(R.id.ImageDetail)
        val title = view.findViewById<TextView>(R.id.TitleNews)
        val layout = view.findViewById<View>(R.id.layoutNews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item,parent,false)
        return  Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image = data[position].image
        holder.title.text = data[position].title
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(holder.ImageNews)
        holder.layout.setOnClickListener {
            val start = Intent(activity,DetailNewsActivity::class.java)
            val str = Gson().toJson(data[position],News::class.java)
            start.putExtra("extra",str)
            activity.startActivity(start)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}