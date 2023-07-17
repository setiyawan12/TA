package com.zahro.pneumonia.contract

import com.zahro.pneumonia.model.Article

interface ArticleActivityContract {
    interface View{
        fun showToast(message:String)
        fun attachArticleToRecyler(listArticle: List<Article>)
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun getArticle()
        fun destroy()
    }
}