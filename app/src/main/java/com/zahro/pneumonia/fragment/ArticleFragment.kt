package com.zahro.pneumonia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_article.*
import com.zahro.pneumonia.R
import com.zahro.pneumonia.adapter.ArticleAdapter
import com.zahro.pneumonia.contract.ArticleActivityContract
import com.zahro.pneumonia.model.Article
import com.zahro.pneumonia.presenter.ArticleActivityPresenter

class ArticleFragment : Fragment(),ArticleActivityContract.View {
    private lateinit var presenter:ArticleActivityContract.Presenter
    lateinit var adapterArticle:ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_article,container,false)
        presenter = ArticleActivityPresenter(this)
        presenter.getArticle()
        return  view
    }

    override fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun attachArticleToRecyler(listArticle: List<Article>) {
        rv_article.apply {
            adapterArticle = ArticleAdapter(requireActivity(),listArticle)
            adapter = adapterArticle
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