package com.zahro.pneumonia.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.zahro.pneumonia.contract.HistoryActivityContract
import com.zahro.pneumonia.model.History
import com.zahro.pneumonia.response.WrappedResponse
import com.zahro.pneumonia.util.APIclient

class HistoryActivityPresenter(v:HistoryActivityContract.HistoryActivityView?):HistoryActivityContract.HistoryActivityPresenter {
    private var view:HistoryActivityContract.HistoryActivityView?=v
    private var apiServices = APIclient.APIService()
    override fun addHistory(
        name: String,
        indicated: String,
        alamat: String,
        umur: String,
        no_hp: String,
        image: String,
        presentase: String,
        user_id: Int
    ) {
        val request =apiServices.addHistory(name, indicated, alamat, umur, no_hp, user_id, image, presentase)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<History>>{
            override fun onResponse(
                call: Call<WrappedResponse<History>>,
                response: Response<WrappedResponse<History>>
            ) {
                if (response.isSuccessful){
                    view?.success()
                    view?.hideLoading()
                    view?.showToast("berhasil")
                }
                view?.hideLoading()
            }
            override fun onFailure(call: Call<WrappedResponse<History>>, t: Throwable) {
                view?.showToast("terjadi kesalahan server")
                view?.hideLoading()
            }

        })
    }
    override fun destroy() {
        view = null
    }
}