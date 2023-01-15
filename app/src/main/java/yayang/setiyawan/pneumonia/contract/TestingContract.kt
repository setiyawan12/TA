package yayang.setiyawan.pneumonia.contract

import yayang.setiyawan.pneumonia.model.History

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