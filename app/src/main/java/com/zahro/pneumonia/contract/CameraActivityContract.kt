package com.zahro.pneumonia.contract

import android.content.Context
import android.content.Intent
import okhttp3.MultipartBody

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