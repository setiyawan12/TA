package com.zahro.pneumonia.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
//import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.zahro.pneumonia.R

@RequiresApi(Build.VERSION_CODES.M)
class CropActivity : AppCompatActivity() {
    var userpic :ImageView? = null
    val STORAGE_REQUEST = 200
    var storagePermission:Array<String>? = null
    var imageUri:Uri? = null
    var click: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)
        click = findViewById(R.id.click)
        userpic = findViewById(R.id.set_profile_image)
        click!!.setOnClickListener {
            pickFromGallery()
        }
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
    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED
    }
    private fun pickFromGallery() {
        CropImage.activity().start(this)
    }

    private fun requestStoragePermission() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.getUri()
                userpic?.setImageURI(resultUri)
//                Picasso.with(this).load(resultUri).into(userpic)
            }
        }
    }
}