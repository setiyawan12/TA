package com.zahro.pneumonia.fragment
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zahro.pneumonia.BuildConfig
import com.zahro.pneumonia.R
import com.zahro.pneumonia.adapter.NewsAdapter
import com.zahro.pneumonia.contract.NewsActivityContract
import com.zahro.pneumonia.contract.VersionContract
import com.zahro.pneumonia.model.News
import com.zahro.pneumonia.presenter.NewsActivityPresenter
import com.zahro.pneumonia.presenter.VersionActivityPresenter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),NewsActivityContract.GetNewsView,VersionContract.View{
    private lateinit var presenter :NewsActivityContract.GetNewsPresenter
    private lateinit var presenterVersion:VersionContract.Presenter
    lateinit var adapterNews: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_home,container,false)
        init(view)
        presenter = NewsActivityPresenter(this)
        presenter?.getNews()
        presenterVersion = VersionActivityPresenter(this)
        presenterVersion?.getVersion(BuildConfig.VERSION_CODE)
        return view
    }
    private fun init(view: View){

    }
    override fun showToas(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    private fun dialogShow(){
        val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_custom_version,null)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.setOnShowListener{
            val width = WindowManager.LayoutParams.MATCH_PARENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height) // Mengatur lebar dan tinggi dialog
            dialog.window?.setGravity(Gravity.CENTER)
        }
        val btnClose =  dialogView.findViewById<Button>(R.id.exit)
        val dec = dialogView.findViewById<TextView>(R.id.Description)
        dec.text = "Maaf atas ketidaknyamanan ini. Aplikasi kami sedang menjalani perawatan untuk meningkatkan kinerja dan memberikan pengalaman yang lebih baik kepada Anda. Kami akan segera kembali online. Terima kasih atas kesabarannya"
        btnClose.setOnClickListener {
            dialog.dismiss()
            val activity = requireActivity()
            activity.finish()
        }
        dialog.setCancelable(false)
        dialog.show()
    }
    override fun attachNewsTorecycle(listNews: List<News>) {
        rv_news.apply {
            adapterNews = NewsAdapter(requireActivity(),listNews)
            adapter = adapterNews
            val linearLayoutManager = LinearLayoutManager(requireActivity())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showToastVersion(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showDialogVersion() {
        rv_news.visibility = View.GONE
        dialogShow()
    }

}