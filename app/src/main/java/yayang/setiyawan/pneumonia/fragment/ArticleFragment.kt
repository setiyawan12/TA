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

class ArticleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_article,container,false)
        return  view
//        return inflater.inflate(R.layout.fragment_article, container, false)
    }
}