package com.zahro.pneumonia.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zahro.pneumonia.R
import com.zahro.pneumonia.model.History
import com.zahro.pneumonia.ui.DetailHistoryActivity

class HistoryAdapter(var activity:Activity,var data:List<History>):RecyclerView.Adapter<HistoryAdapter.Holder>(),Filterable {
    var fillterData = ArrayList<History>()
    class Holder(view:View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val image_conf = view.findViewById<ImageView>(R.id.img_confiden)
        val tvConfidence = view.findViewById<TextView>(R.id.tvConfidence)
        val layout = view.findViewById<View>(R.id.layout)
    }
    init {
        fillterData = data as ArrayList<History>
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val nama = fillterData[position].name
        val image = fillterData[position].image
        val confident = fillterData[position].indicated
        holder.tvNama.text = nama
        holder.tvConfidence.text = confident
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(holder.image_conf)
        holder.layout.setOnClickListener {
            val activiti = Intent(activity,DetailHistoryActivity::class.java)
            val str = Gson().toJson(data[position],History::class.java)
            activiti.putExtra("extra",str)
            activity.startActivity(activiti)
        }
    }
    override fun getItemCount(): Int {
        return fillterData.size
    }
    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    fillterData = data as ArrayList<History>
                } else {
                    var searchChr = constraint.toString().lowercase()
                    val resultList = ArrayList<History>()
                    for (row in data) {
                        if (row.name!!.contains(searchChr)) {
                            resultList.add(row)
                        }
                    }
                    fillterData = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = fillterData
                return filterResults
            }
            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                fillterData = filterResults?.values as ArrayList<History>
                notifyDataSetChanged()
            }
        }
    }
}