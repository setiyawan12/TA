package com.zahro.pneumonia.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.zahro.pneumonia.contract.RegisterActivityContract
import com.zahro.pneumonia.model.User
import com.zahro.pneumonia.response.WrappedResponse
import com.zahro.pneumonia.util.APIclient

class RegisterActivityPresenter(v:RegisterActivityContract.RegisterActivityView?):RegisterActivityContract.RegisterActivityPresenter {
    private var view:RegisterActivityContract.RegisterActivityView?=v
    private var  apiServices = APIclient.APIService()
    override fun register(name: String, email: String, password: String) {
        val request =apiServices.register(name, email, password)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>>{
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    view?.hideLoading()
                    view?.successRegister()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("terjadi kesalahan server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}