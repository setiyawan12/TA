package com.zahro.pneumonia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zahro.pneumonia.contract.RegisterActivityContract
import com.zahro.pneumonia.databinding.ActivitySignUpBinding
import com.zahro.pneumonia.presenter.RegisterActivityPresenter

class SignUpActivity : AppCompatActivity(),RegisterActivityContract.RegisterActivityView {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var presenter: RegisterActivityContract.RegisterActivityPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        presenter = RegisterActivityPresenter(this)
        setContentView(binding.root)
        register()
        login()
    }
    private fun register() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && isValidEmail(email) && password.length >= 6) {
                presenter.register(name, email, password)
            } else {
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    showToast("Please Input All form")
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

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun successRegister() {
        startActivity(Intent(this,LoginActivity::class.java).also { finish() })
    }
    override fun showLoading() {
        binding.loadingRegister.apply {
            isIndeterminate=true
            visibility = View.VISIBLE
        }
    }
    private fun login(){
        binding.btnToLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java).apply {
                finish()
            })
        }
    }
    override fun hideLoading() {
        binding.loadingRegister.apply {
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