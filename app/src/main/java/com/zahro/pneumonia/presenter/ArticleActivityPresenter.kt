package com.zahro.pneumonia.presenter

import com.zahro.pneumonia.contract.ArticleActivityContract
import com.zahro.pneumonia.model.Article
import com.zahro.pneumonia.response.WrappedListResponse
import com.zahro.pneumonia.util.APIclient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleActivityPresenter (v:ArticleActivityContract.View?):ArticleActivityContract.Presenter{
    private var view:ArticleActivityContract.View?=v
    private var apiServices = APIclient.APIService()
    override fun getArticle() {
        val request = apiServices.article()
        request.enqueue(object  :Callback<WrappedListResponse<Article>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Article>>,
                response: Response<WrappedListResponse<Article>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.attachArticleToRecyler(body.data)
                    }
                }
            }
            override fun onFailure(call: Call<WrappedListResponse<Article>>, t: Throwable) {
               view?.showToast("Terjadi Kesalahan Server")
            }
        })
    }
    override fun destroy() {
    }
}