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
        mainButton()
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
                    startActivity(Intent(this,CameraActivity::class.java).apply {
                        finish()
                    })
                }
                R.id.nvCreateHistory->{
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Notice")
                        .setContentText("Apakah prediksi ini sudah sesuai menurut anda")
                        .setCancelText("No")
                        .setConfirmText("Yes")
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
            if (nama.isNotEmpty()&& !nama.any{it.isDigit()}&&alamat.isNotEmpty()&&umur.isNotEmpty()&&nomor.isNotEmpty()){
                if (nomor.length in 11..13){
                    presenter?.addHistory(nama, result.toString(), alamat, umur, nomor, url.toString(), presentase.toString(), userid)
                    dialog.setCancelable(false)
                    dialog.show()
                }else{
                    Toast.makeText(this, "Nomor harus memiliki panjang 11 hingga 13 karakter", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Nama tidak boleh mengandung angka atau isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
    override fun success() {
        startActivity(Intent(this, MainActivity::class.java).also { finish() })
    }
    override fun showLoading() {

    }

    override fun hideLoading() {
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