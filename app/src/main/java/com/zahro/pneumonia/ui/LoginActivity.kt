package com.zahro.pneumonia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zahro.pneumonia.MainActivity
import com.zahro.pneumonia.contract.LoginActivityContract
import com.zahro.pneumonia.databinding.ActivityLoginBinding
import com.zahro.pneumonia.presenter.LoginActivityPresenter
import com.zahro.pneumonia.util.Constants

class LoginActivity : AppCompatActivity(),LoginActivityContract.LoginActivityView {
    private lateinit var presenter:LoginActivityContract.LoginActivityPresenter
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = LoginActivityPresenter(this)
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
}