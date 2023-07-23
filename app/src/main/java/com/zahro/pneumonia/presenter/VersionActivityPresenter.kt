package com.zahro.pneumonia.presenter

import com.zahro.pneumonia.contract.VersionContract
import com.zahro.pneumonia.response.WrappedObject
import com.zahro.pneumonia.util.APIclient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VersionActivityPresenter(v:VersionContract.View):VersionContract.Presenter {
    private  var view:VersionContract.View?=v
    private  var apiServices = APIclient.APIService()
    override fun getVersion(id: Int) {
        val request = apiServices.version(id)
        request.enqueue(object :Callback<WrappedObject>{
            override fun onResponse(call: Call<WrappedObject>, response: Response<WrappedObject>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.status==401){
                        view?.showDialogVersion()
                    }
                }
            }
            override fun onFailure(call: Call<WrappedObject>, t: Throwable) {
                view?.showDialogVersion()
            }

        })
    }

    override fun destroy() {
    }

}