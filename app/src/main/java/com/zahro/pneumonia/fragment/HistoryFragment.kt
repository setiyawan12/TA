package com.zahro.pneumonia.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.view.*
import com.zahro.pneumonia.R
import com.zahro.pneumonia.adapter.HistoryAdapter
import com.zahro.pneumonia.contract.HistoryActivityContract
import com.zahro.pneumonia.model.History
import com.zahro.pneumonia.presenter.GetHistoryActivityPresenter
import com.zahro.pneumonia.util.Constants
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
    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}

