package yayang.setiyawan.pneumonia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import yayang.setiyawan.pneumonia.R
import yayang.setiyawan.pneumonia.contract.RegisterActivityContract
import yayang.setiyawan.pneumonia.databinding.ActivitySignUpBinding
import yayang.setiyawan.pneumonia.presenter.RegisterActivityPresenter

class SignUpActivity : AppCompatActivity(),RegisterActivityContract.RegisterActivityView {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var presenter: RegisterActivityContract.RegisterActivityPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        presenter = RegisterActivityPresenter(this)
        setContentView(binding.root)
        register()
    }
    private fun register(){
        binding.btnRegister.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (name.isNotEmpty()&&email.isNotEmpty()&&password.isNotEmpty()){
                presenter.register(name, email, password)
            }else{
                showToast("Please Input All form")
            }
        }
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