package com.zahro.pneumonia.contract

import com.zahro.pneumonia.model.News

interface NewsActivityContract {
    interface GetNewsView{
        fun showToas(message:String)
        fun attachNewsTorecycle(listNews:List<News>)
        fun showLoading()
        fun hideLoading()
    }
    interface GetNewsPresenter{
        fun getNews()
        fun destroy()
    }
}