package com.zahro.pneumonia.contract

import android.content.Context

interface LoginActivityContract {
    interface LoginActivityView{
        fun showToast(message:String)
        fun successLogin()
        fun showLoading()
        fun hideLoading()
    }
    interface  LoginActivityPresenter{
        fun login(email:String,password:String,context: Context)
        fun destroy()
    }
}