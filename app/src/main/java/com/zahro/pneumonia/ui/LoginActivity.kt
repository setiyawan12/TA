package com.zahro.pneumonia.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.zahro.pneumonia.BuildConfig
import com.zahro.pneumonia.MainActivity
import com.zahro.pneumonia.R
import com.zahro.pneumonia.contract.LoginActivityContract
import com.zahro.pneumonia.contract.VersionContract
import com.zahro.pneumonia.databinding.ActivityLoginBinding
import com.zahro.pneumonia.presenter.LoginActivityPresenter
import com.zahro.pneumonia.presenter.VersionActivityPresenter
import com.zahro.pneumonia.util.Constants

class LoginActivity : AppCompatActivity(),LoginActivityContract.LoginActivityView,VersionContract.View {
    private lateinit var presenter:LoginActivityContract.LoginActivityPresenter
    private  lateinit var presenterVersion:VersionContract.Presenter
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = LoginActivityPresenter(this)
        presenterVersion = VersionActivityPresenter(this)
        presenterVersion?.getVersion(BuildConfig.VERSION_CODE)
        login()
        register()
    }
    override fun onStart() {
        super.onStart()
        isLogin()
    }
    private fun isLogin(){
        val token = Constants.getToken(this)
        if (token.isNotEmpty() && token != "UNDEFINED"){
            startActivity(Intent(this, MainActivity::class.java).also { finish() })
        }
    }
    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
    override fun successLogin() {
        startActivity(Intent(this,MainActivity::class.java).also { finish() })
    }
    private fun login(){
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                presenter.login(email,password,this)
            }else{
                showToast("Please Input All Form")
            }
        }
    }
    private fun register(){
        binding.tvCreateAccount.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
    override fun showLoading() {
        binding.loadingLogin.apply {
            isIndeterminate=true
            visibility = View.VISIBLE
        }
    }
    override fun hideLoading() {
        binding.loadingLogin.apply {
            isIndeterminate=false
            progress =0
            visibility = View.GONE
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
    override fun showToastVersion(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun showDialogVersion() {
        binding.layoutLogin.visibility = View.GONE
        dialogShow()
    }
    private fun dialogShow(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_version,null)
        val builder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.setOnShowListener{
            val width = WindowManager.LayoutParams.MATCH_PARENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height) // Mengatur lebar dan tinggi dialog
            dialog.window?.setGravity(Gravity.CENTER)
        }
        val btnClose =  dialogView.findViewById<Button>(R.id.exit)
        btnClose.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}