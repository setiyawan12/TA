package yayang.setiyawan.pneumonia.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.view.*
import yayang.setiyawan.pneumonia.R
import yayang.setiyawan.pneumonia.adapter.HistoryAdapter
import yayang.setiyawan.pneumonia.contract.HistoryActivityContract
import yayang.setiyawan.pneumonia.model.History
import yayang.setiyawan.pneumonia.presenter.GetHistoryActivityPresenter
import yayang.setiyawan.pneumonia.util.Constants
import java.util.*
import kotlin.collections.ArrayList

class HistoryFragment : Fragment(),HistoryActivityContract.GetHistoryView {
    private lateinit var presenter: HistoryActivityContract.GetHistoryPresenter
    lateinit var adapterHistory: HistoryAdapter
    private var mlist = ArrayList<History>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        init(view)
        presenter = GetHistoryActivityPresenter(this)
        val getuser = Constants.getId(requireActivity())
        presenter?.getHistory(getuser)
        return view
    }

    fun init(view: View) {

    }

    override fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun attachNewsToRecycler(listHistory: List<History>) {
        view?.rv_history?.apply {
            adapterHistory = HistoryAdapter(requireActivity(), listHistory)
            adapter = adapterHistory
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
        }
        view?.search_view?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapterHistory.filter.filter(newText)
                return true
            }
        })
    }

    private fun filterList(query:String?){
        if (query != null){
            val filteredList = ArrayList<History>()
            for ( i in mlist){
                if (i.name?.toLowerCase(Locale.ROOT)!!.contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                showToast("No Data Found")
            }else{
                adapterHistory.filter.filter(query)
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu, menu)
//        val menuItem = menu.findItem(R.id.search_view)
//        val searchView = menuItem.actionView as SearchView
//        searchView.maxWidth = Int.MAX_VALUE
//        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                adapterHistory.filter.filter(query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapterHistory.filter.filter(newText)
//                return true
//            }
//
//        })
//    }
    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}

