package yayang.setiyawan.pneumonia.presenter

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.pneumonia.contract.RegisterActivityContract
import yayang.setiyawan.pneumonia.model.User
import yayang.setiyawan.pneumonia.response.WrappedResponse
import yayang.setiyawan.pneumonia.util.APIclient
import yayang.setiyawan.pneumonia.webservices.APIServices

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