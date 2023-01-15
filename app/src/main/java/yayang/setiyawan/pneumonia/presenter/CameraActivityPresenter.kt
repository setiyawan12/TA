package yayang.setiyawan.pneumonia.presenter

import android.content.Context
import android.content.Intent
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.pneumonia.contract.CameraActivityContract
import yayang.setiyawan.pneumonia.model.Results
import yayang.setiyawan.pneumonia.response.WrappedResponse
import yayang.setiyawan.pneumonia.ui.PredictionActivity
import yayang.setiyawan.pneumonia.util.APIclient

class CameraActivityPresenter(v:CameraActivityContract.View?):CameraActivityContract.Presenter {
    private var view:CameraActivityContract.View? = v
    private var apiServices = APIclient.APIService()
    override fun prediction(image: MultipartBody.Part,context: Context) {
        val request = apiServices.predict(image)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<Results>>{
            override fun onResponse(
                call: Call<WrappedResponse<Results>>,
                response: Response<WrappedResponse<Results>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        val cek =body.data.result
                        view?.showToast(cek.toString())
                        view?.hideLoading()
                        val result = body.data.result
                        val url = body.data.secure_url
                        val intent = Intent(context,PredictionActivity::class.java)
                        intent.putExtra("result",result)
                        intent.putExtra("url",url)
                        view?.successPredict(intent)
                    }
                }
                view?.hideLoading()
            }
            override fun onFailure(call: Call<WrappedResponse<Results>>, t: Throwable) {
                view?.showToast("terjadi kesalahan server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
       view = null
    }

}