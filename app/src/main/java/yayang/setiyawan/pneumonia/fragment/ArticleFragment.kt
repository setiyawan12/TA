package yayang.setiyawan.pneumonia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_article.*
import yayang.setiyawan.pneumonia.R
import yayang.setiyawan.pneumonia.adapter.TestAdapter
import yayang.setiyawan.pneumonia.contract.HistoryActivityContract
import yayang.setiyawan.pneumonia.model.History
import yayang.setiyawan.pneumonia.presenter.GetHistoryActivityPresenter
import yayang.setiyawan.pneumonia.util.Constants

class ArticleFragment : Fragment(),HistoryActivityContract.GetHistoryView {
    private lateinit var presenter:HistoryActivityContract.GetHistoryPresenter
    lateinit var testAdapter: TestAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_article,container,false)
        presenter = GetHistoryActivityPresenter(this)
        val getUser = Constants.getId(requireActivity())
        presenter?.getHistory(getUser)
        return  view
//        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun attachNewsToRecycler(listHistory: List<History>) {
        rv_test.apply {
            testAdapter = TestAdapter(ArrayList(listHistory))
            adapter = testAdapter
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
        }
        search_view.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                testAdapter.filter.filter(newText)
                return false
            }

        })
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}