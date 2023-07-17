package com.zahro.pneumonia.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.zahro.pneumonia.contract.HistoryActivityContract
import com.zahro.pneumonia.model.History
import com.zahro.pneumonia.response.WrappedListResponse
import com.zahro.pneumonia.util.APIclient

class GetHistoryActivityPresenter(v:HistoryActivityContract.GetHistoryView?):HistoryActivityContract.GetHistoryPresenter {
    private var view:HistoryActivityContract.GetHistoryView?=v
    private var apiServices = APIclient.APIService()
    override fun getHistory(id: Int) {
        val request = apiServices.history(id)
        view?.showLoading()
        request.enqueue(object :Callback<WrappedListResponse<History>>{
            override fun onResponse(
                call: Call<WrappedListResponse<History>>,
                response: Response<WrappedListResponse<History>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.attachNewsToRecycler(body.data)
                    }else{
                        view?.showToast("Data is empty")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Check your connection")
                    view?.hideLoading()
                }
                view?.hideLoading()
            }
            override fun onFailure(call: Call<WrappedListResponse<History>>, t: Throwable) {
                view?.showToast("Cant connect to server")
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }
}