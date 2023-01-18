package com.zahro.pneumonia.contract

import com.zahro.pneumonia.model.History

interface TestingContract {
    interface GetHistoryView{
        fun showToast(message:String)
        fun attachNewsToRecycler(listHistory: ArrayList<History>)
        fun showLoading()
        fun hideLoading()
    }
    interface GetHistoryPresenter{
        fun getHistory(id:Int)
        fun destroy()
    }
}