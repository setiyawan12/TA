package com.zahro.pneumonia.presenter

import com.zahro.pneumonia.contract.NewsActivityContract
import com.zahro.pneumonia.model.News
import com.zahro.pneumonia.response.WrappedListResponse
import com.zahro.pneumonia.util.APIclient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivityPresenter(v:NewsActivityContract.GetNewsView?):NewsActivityContract.GetNewsPresenter {
    private var view:NewsActivityContract.GetNewsView?=v
    private var apiServices = APIclient.APIService()
    override fun getNews() {
        val request = apiServices.news()
        request.enqueue(object :Callback<WrappedListResponse<News>>{
            override fun onResponse(
                call: Call<WrappedListResponse<News>>,
                response: Response<WrappedListResponse<News>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.attachNewsTorecycle(body.data)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<News>>, t: Throwable) {
                view?.showToas("terjadi kesalahan server")
            }

        })
    }


    override fun destroy() {
        TODO("Not yet implemented")
    }
}