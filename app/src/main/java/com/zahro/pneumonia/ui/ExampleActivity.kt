package com.zahro.pneumonia.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.theartofdev.edmodo.cropper.CropImage
import com.zahro.pneumonia.R
import kotlinx.android.synthetic.main.activity_example.*

@RequiresApi(Build.VERSION_CODES.M)
class ExampleActivity : AppCompatActivity() {
    var userpic : Uri? = null
    val STORAGE_REQUEST = 200
    var storagePermission:Array<String>?=null
    var choseImage:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        choseImage = findViewById<Button>(R.id.pick)
//        userpic = findViewById(R.id.gambar)
        choseImage!!.setOnClickListener {
            pickFromGallery()
        }
        btnUploadListener()
    }
    private fun showImagePicDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Pick Image From")
            .setPositiveButton("Gallery"){dialog,_->
                if (!checkStoragePermission()){
                    requestStoragePermission()
                }else{
                    pickFromGallery()
                }
            }
        builder.create().show()
    }
    private fun btnUploadListener(){
        uploadImage.setOnClickListener {
            if (userpic == null){
                SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Please insert image")
                    .show()
            }else{
                doUploadImage()
            }
        }
    }

    private fun doUploadImage() {
        SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("berhasil")
            .show()
    }

    private fun checkStoragePermission():Boolean{
        return ContextCompat.checkSelfPermission(
            this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_DENIED
    }
    private fun requestStoragePermission(){
        requestPermissions(storagePermission!!,STORAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array < String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_REQUEST){
            if (grantResults.size> 0) {
                val writeStoregeaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (writeStoregeaccepted){
                    pickFromGallery()
                }else{
                    Toast.makeText(this,
                        "Please Enable Storage Permission",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun pickFromGallery(){
        CropImage.activity().start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result : CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK){
                val resultUri:Uri = result.getUri()
//                gambar?.setImageURI(resultUri)
//                userpic = data?.data
                gambar.setImageURI(resultUri)
            }
        }
    }
}