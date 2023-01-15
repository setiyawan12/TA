package yayang.setiyawan.pneumonia.presenter

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.pneumonia.contract.LoginActivityContract
import yayang.setiyawan.pneumonia.model.User
import yayang.setiyawan.pneumonia.response.WrappedResponse
import yayang.setiyawan.pneumonia.util.APIclient
import yayang.setiyawan.pneumonia.util.Constants

class LoginActivityPresenter(v:LoginActivityContract.LoginActivityView?):LoginActivityContract.LoginActivityPresenter {
    private var view : LoginActivityContract.LoginActivityView?=v
    private var apiServices = APIclient.APIService()
    override fun login(email: String, password: String, context: Context) {
        val request = apiServices.login(email, password)
        view?.showLoading()
        request.enqueue(object :Callback<WrappedResponse<User>>{
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast("Success Login")
                        view?.successLogin()
                        view?.hideLoading()
                        Constants.setToken(context,body.data.token.toString())
                        Constants.setName(context,body.data.name.toString())
                        Constants.setId(context,body.data.id!!)
                    }else{
                        view?.showToast("Data Is Empty")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Email and Password Doesn't macth")
                }
                view?.hideLoading()
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