package yayang.setiyawan.pneumonia.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.pneumonia.contract.HistoryActivityContract
import yayang.setiyawan.pneumonia.model.History
import yayang.setiyawan.pneumonia.response.WrappedResponse
import yayang.setiyawan.pneumonia.util.APIclient

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
        user_id: Int
    ) {
        val request = apiServices.addHistory(name, indicated, alamat, umur, no_hp, user_id, image)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<History>>{
            override fun onResponse(
                call: Call<WrappedResponse<History>>,
                response: Response<WrappedResponse<History>>
            ) {
                if (response.isSuccessful){
                    view?.success()
                    view?.hideLoading()
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