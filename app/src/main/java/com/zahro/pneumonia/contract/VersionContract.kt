package com.zahro.pneumonia.contract

interface VersionContract {
    interface View{
        fun showToastVersion(message:String)
        fun showDialogVersion()
    }
    interface Presenter{
        fun getVersion(id:Int)
        fun destroy()
    }
}