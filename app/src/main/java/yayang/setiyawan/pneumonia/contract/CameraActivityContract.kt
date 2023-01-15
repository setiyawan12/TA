package yayang.setiyawan.pneumonia.contract

import android.content.Context
import android.content.Intent
import okhttp3.MultipartBody
import retrofit2.http.Multipart

interface CameraActivityContract {
    interface View{
        fun showToast(message : String)
        fun successPredict(pindah:Intent)
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun prediction(image:MultipartBody.Part,context: Context)
        fun destroy()
    }
}