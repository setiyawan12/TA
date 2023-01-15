package yayang.setiyawan.pneumonia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_history.view.*
import yayang.setiyawan.pneumonia.R
import yayang.setiyawan.pneumonia.model.History


class TestAdapter(private var data:List<History>):RecyclerView.Adapter<TestAdapter.TestViewHolder>(),Filterable {
    var filldata = ArrayList<History>()
    inner class TestViewHolder (view:View):RecyclerView.ViewHolder(view)
    init {
        filldata = data as ArrayList<History>
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
     val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history,parent,false)
        val sch = TestViewHolder(v)
        return sch
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
       val nama = filldata[position].name
        holder.itemView.tv_nama.text = nama
    }

    override fun getItemCount(): Int {
        return filldata.size
    }

    override fun getFilter(): Filter {
        return  object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filldata = data as ArrayList<History>
                } else {
                    var searchChr = constraint.toString().lowercase()
                    val resultList = ArrayList<History>()
                    for (row in data) {
                        if (row.name!!.contains(searchChr)) {
                            resultList.add(row)
                        }
                    }
                    filldata = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filldata
                return filterResults
            }
            override fun publishResults(constaint: CharSequence?, results: FilterResults?) {
                filldata=results?.values as ArrayList<History>
                notifyDataSetChanged()
            }
        }
    }
}