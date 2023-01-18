package com.zahro.pneumonia.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import com.zahro.pneumonia.contract.CameraActivityContract
import com.zahro.pneumonia.databinding.ActivityTestingBinding
import com.zahro.pneumonia.presenter.CameraActivityPresenter
import com.zahro.pneumonia.response.UploadRequestBody
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class TestingActivity : AppCompatActivity(),UploadRequestBody.UploadCallback,CameraActivityContract.View {
    private var presenter:CameraActivityContract.Presenter?=null
    private lateinit var binding: ActivityTestingBinding
    private var selectedImageUri:Uri? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = CameraActivityPresenter(this)
        binding.BtnChooseImage.setOnClickListener {
            getImage()
        }
        binding.BtnUpload.setOnClickListener {
            doUploadImage()
        }
    }

    private fun doUploadImage() {
        if (selectedImageUri == null){
            Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show()
        }
        val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedImageUri!!,"r",null)?:return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir,contentResolver.getFileName(selectedImageUri!!))
        val outputSteram = FileOutputStream(file)
        inputStream.copyTo(outputSteram)
        val foto = UploadRequestBody(file,"image",this)
        val image = MultipartBody.Part.createFormData("file",file.name,foto)
        presenter?.prediction(image,this)
    }
    private fun getImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode==100){
            selectedImageUri= data?.data
            binding.ImageDetail.setImageURI(selectedImageUri)
        }
    }

    override fun onProgressUpdate(percentage: Int) {

    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    override fun successPredict(pindah: Intent) {
        startActivity(pindah)
    }

    override fun showLoading() {
        binding.loadingPredict.apply {
            isIndeterminate=true
            visibility = View.VISIBLE
        }
        binding.apply {
            BtnUpload.isEnabled = false
            BtnChooseImage.isEnabled = false
            BtnCamera.isEnabled = false
        }
    }

    override fun hideLoading() {
        binding.loadingPredict.apply {
            isIndeterminate=false
            progress =0
            visibility = View.GONE
        }
        binding.apply {
            BtnUpload.isEnabled = true
            BtnChooseImage.isEnabled = true
            BtnCamera.isEnabled = true
        }
    }
}
private fun ContentResolver.getFileName(fileUri: Uri): String {
var name = ""
    val returnCursor = this.query(fileUri,null,null,null,null)
    if (returnCursor != null){
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}
