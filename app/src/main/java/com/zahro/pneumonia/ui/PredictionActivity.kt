package com.zahro.pneumonia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight=200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        mainButton()
        save()
        setValue()
    }
    private fun mainButton(){
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,CameraActivity::class.java).apply {
                finish()
            })
        }
    }

    private fun save(){
        binding.btnHistory.setOnClickListener {
            val nama = binding.etNamaPasien.text.toString()
            val alamat = binding.etAlamatPasien.text.toString()
            val umur = binding.etUmurPasien.text.toString()
            val no = binding.etNoHpPasien.text.toString()
            val result = intent.getStringExtra("result")
            val url = intent.getStringExtra("url")
            val presentase = intent.getStringExtra("presentase")
            val user_id = Constants.getId(this)
            if (nama.isNotEmpty() && alamat.isNotEmpty() && umur.isNotEmpty() && no.isNotEmpty()){
                presenter?.addHistory(nama,result.toString(),alamat,umur,no,url.toString(),presentase.toString(),user_id)
            }else{
                Toast.makeText(this, "isi semua form", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
    override fun success() {
        startActivity(Intent(this, MainActivity::class.java).also { finish() })
    }
    override fun showLoading() {
        binding.progressBar.apply {
            isIndeterminate=true
            visibility = View.VISIBLE
        }
        binding.apply {
            btnHistory.isEnabled=false
        }
    }

    override fun hideLoading() {
        binding.progressBar.apply {
            isIndeterminate=false
            progress =0
            visibility = View.GONE
        }
        binding.apply {
            btnHistory.isEnabled=true
        }
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