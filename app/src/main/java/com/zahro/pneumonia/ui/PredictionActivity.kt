package com.zahro.pneumonia.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_prediction.*
import com.zahro.pneumonia.MainActivity
import com.zahro.pneumonia.R
import com.zahro.pneumonia.contract.HistoryActivityContract
import com.zahro.pneumonia.databinding.ActivityPredictionBinding
import com.zahro.pneumonia.presenter.HistoryActivityPresenter
import com.zahro.pneumonia.util.Constants

class PredictionActivity : AppCompatActivity(),HistoryActivityContract.HistoryActivityView {
    private lateinit var presenter: HistoryActivityContract.HistoryActivityPresenter
    private lateinit var binding: ActivityPredictionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = HistoryActivityPresenter(this)
//        BottomSheetBehavior.from(binding.sheet).apply {
//            peekHeight=200
//            this.state = BottomSheetBehavior.STATE_COLLAPSED
//        }
        mainButton()
//        save()
        setValue()
        buttonNav()
    }
    private fun mainButton(){
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,CameraActivity::class.java).apply {
                finish()
            })
        }
    }
    private fun buttonNav(){
        binding.bottoNavPred.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nvBack->{
                    Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
                }
                R.id.nvCreateHistory->{
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Notice")
                        .setContentText("Apakah prediksi ini sudah sesuai menurut anda")
                        .setCancelText("No, cancel plx!")
                        .setConfirmText("Yes, delete it!")
                        .showCancelButton(true)
                        .setCancelClickListener { sDialog ->
                            sDialog.cancel()
                        }
                        .setConfirmClickListener {sDialog ->
                            sDialog.cancel()
                            dialogShow()
                        }
                        .show()
                }
            }
            true
        }
    }
    private fun dialogShow(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_create_history,null)
        val builder = AlertDialog.Builder(this,R.style.CustomDialogTheme)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.setOnShowListener{
            val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height) // Mengatur lebar dan tinggi dialog
        }
        val btnClose =  dialogView.findViewById<ImageView>(R.id.btnClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        val btnSave = dialogView.findViewById<Button>(R.id.btnSave)
        val etNamaPasien = dialogView.findViewById<EditText>(R.id.etNamaPasien)
        val etAlamatPasien = dialogView.findViewById<EditText>(R.id.etAlamatPasien)
        val etUmurPasien = dialogView.findViewById<EditText>(R.id.etUmurPasien)
        val etNomorPasien = dialogView.findViewById<EditText>(R.id.etNomorPasien)
        btnSave.setOnClickListener {
            val nama = etNamaPasien.text.toString()
            val alamat = etAlamatPasien.text.toString()
            val umur = etUmurPasien.text.toString()
            val nomor = etNomorPasien.text.toString()
            val result = intent.getStringExtra("result")
            val url = intent.getStringExtra("url")
            val presentase = intent.getStringExtra("presentase")
            val userid = Constants.getId(this)
            if (nama.isNotEmpty()&&alamat.isNotEmpty()&&umur.isNotEmpty()&&nomor.isNotEmpty()){
                presenter?.addHistory(nama,result.toString(),alamat,umur,nomor,url.toString(),presentase.toString(),userid)
            }else{
                Toast.makeText(this, "isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setCancelable(false)
//        dialog.window?.setLayout(dialogWidth,dialogHeight)
        dialog.show()
    }

//    private fun save(){
//        binding.btnHistory.setOnClickListener {
//            val nama = binding.etNamaPasien.text.toString()
//            val alamat = binding.etAlamatPasien.text.toString()
//            val umur = binding.etUmurPasien.text.toString()
//            val no = binding.etNoHpPasien.text.toString()
//            val result = intent.getStringExtra("result")
//            val url = intent.getStringExtra("url")
//            val presentase = intent.getStringExtra("presentase")
//            val user_id = Constants.getId(this)
//            if (nama.isNotEmpty() && alamat.isNotEmpty() && umur.isNotEmpty() && no.isNotEmpty()){
//                presenter?.addHistory(nama,result.toString(),alamat,umur,no,url.toString(),presentase.toString(),user_id)
//            }else{
//                Toast.makeText(this, "isi semua form", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
    override fun success() {
        startActivity(Intent(this, MainActivity::class.java).also { finish() })
    }
    override fun showLoading() {
//        binding.progressBar.apply {
//            isIndeterminate=true
//            visibility = View.VISIBLE
//        }
//        binding.apply {
//            btnHistory.isEnabled=false
//        }
    }

    override fun hideLoading() {
//        binding.progressBar.apply {
//            isIndeterminate=false
//            progress =0
//            visibility = View.GONE
//        }
//        binding.apply {
//            btnHistory.isEnabled=true
//        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
    private fun setValue(){
        val result = intent.getStringExtra("result")
        val url = intent.getStringExtra("url")
        val presentase = intent.getStringExtra("presentase")
        binding.tvConfidence.text = result
        binding.tvPercent.text=presentase
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(ivResult)
    }
}