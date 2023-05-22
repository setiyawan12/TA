package com.zahro.pneumonia.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zahro.pneumonia.R
import com.zahro.pneumonia.adapter.NewsAdapter
import com.zahro.pneumonia.contract.NewsActivityContract
import com.zahro.pneumonia.model.News
import com.zahro.pneumonia.presenter.NewsActivityPresenter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),NewsActivityContract.GetNewsView{
    private lateinit var presenter :NewsActivityContract.GetNewsPresenter
    lateinit var adapterNews: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_home,container,false)
        init(view)
        presenter = NewsActivityPresenter(this)
        presenter?.getNews()
        return view
    }
    private fun init(view: View){

    }

    override fun showToas(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun attachNewsTorecycle(listNews: List<News>) {
        rv_news.apply {
            adapterNews = NewsAdapter(requireActivity(),listNews)
            adapter = adapterNews
            val linearLayoutManager = LinearLayoutManager(requireActivity())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}