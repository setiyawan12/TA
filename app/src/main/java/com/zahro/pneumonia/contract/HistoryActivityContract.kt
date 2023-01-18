package com.zahro.pneumonia.contract

import com.zahro.pneumonia.model.History

interface HistoryActivityContract {
    interface HistoryActivityView{
        fun showToast(message:String)
        fun success()
        fun showLoading()
        fun hideLoading()
    }
    interface HistoryActivityPresenter{
        fun addHistory(
            name:String,
            indicated:String,
            alamat:String,
            umur:String,
            no_hp:String,
            image:String,
            presentase:String,
            user_id: Int
        )
        fun destroy()
    }
    interface GetHistoryView{
        fun showToast(message:String)
        fun attachNewsToRecycler(listHistory: List<History>)
        fun showLoading()
        fun hideLoading()
    }
    interface GetHistoryPresenter{
        fun getHistory(id:Int)
        fun destroy()
    }

}