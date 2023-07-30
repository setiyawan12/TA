package com.zahro.pneumonia.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        installSplashScreen()
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
    private fun login() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && isValidEmail(email) && password.length >= 6) {
                presenter.login(email, password, this)
            } else {
                if (email.isEmpty() || password.isEmpty()) {
                    showToast("Please Input All Form")
                } else if (!isValidEmail(email)) {
                    showToast("Please enter a valid email address")
                } else {
                    showToast("Password should be at least 6 characters long")
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
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