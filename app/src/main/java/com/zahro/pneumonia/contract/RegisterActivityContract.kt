package com.zahro.pneumonia.contract

interface RegisterActivityContract {
    interface RegisterActivityView{
        fun showToast(message:String)
        fun successRegister()
        fun showLoading()
        fun hideLoading()
    }
    interface  RegisterActivityPresenter{
        fun register(name:String,email:String,password:String)
        fun destroy()
    }
}