package com.zahro.pneumonia.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zahro.pneumonia.databinding.FragmentAkunBinding
import com.zahro.pneumonia.ui.LoginActivity
import com.zahro.pneumonia.util.Constants

class AkunFragment : Fragment() {
    private var _binding:FragmentAkunBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAkunBinding.inflate(inflater,container,false)
        btnLogout()
        setvalue()
        return  binding.root
    }
    private fun btnLogout()
    {
        showAlertDialogue()
    }
    private fun logout(){
        val intent = Intent(activity,LoginActivity::class.java).also {
            Constants.clearName(requireActivity())
            Constants.clearId(requireActivity())
            Constants.clearToken(requireActivity())
        }
        activity?.startActivity(intent)
        activity?.finish()
    }
    private fun showAlertDialogue() {
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Log Out")
            builder.setMessage("Are you sure")
            builder.setPositiveButton("yes"){
                dialog,which->
                logout()
            }
            builder.setNegativeButton("Cancle"){
                dialog,which->dialog.cancel()
            }
            builder.show()
        }
    }
    private fun setvalue(){
        val name = Constants.getName(requireActivity())
        binding.tvNama.text = name
    }
}