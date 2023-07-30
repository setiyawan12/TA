package com.zahro.pneumonia.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.zahro.pneumonia.MainActivity
import okhttp3.MultipartBody
import com.zahro.pneumonia.contract.CameraActivityContract
import com.zahro.pneumonia.databinding.ActivityCameraBinding
import com.zahro.pneumonia.presenter.CameraActivityPresenter
import com.zahro.pneumonia.response.UploadRequestBody
import kotlinx.android.synthetic.main.activity_camera.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity(),CameraActivityContract.View,UploadRequestBody.UploadCallback {
    private var presenter:CameraActivityContract.Presenter?=null
    private var selectedImageUri: Uri? =null
    private lateinit var binding: ActivityCameraBinding
    private lateinit var currentPhotoPath: String
    private val REQUEST_TAKE_PHOTO = 1
    private var CROP_PIC = 2
    private lateinit var photoFile:File
    private val GALLERY_REQUEST_CODE = 1234
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CameraActivityPresenter(this)
        binding= ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnChooseListener()
        btnUploadListener()
        back()
    }
    private fun back(){
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java).apply {
                finish()
            })
        }
    }
    private fun btnChooseListener(){
        binding.BtnChooseImage.setOnClickListener {
            chooseImage()
        }
    }
    private fun btnUploadListener(){
        binding.BtnUpload.setOnClickListener {
            if (selectedImageUri == null){
                SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Please Inset Image")
                    .show()
            }else{
                doUploadImage()
            }
        }
    }
    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GALLERY_REQUEST_CODE->{
                if (resultCode == Activity.RESULT_OK){
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }else{
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory." )
                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ->{
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK){
                    selectedImageUri = result.uri
                    binding.ImageDetail.setImageURI(selectedImageUri)
                }
            }
        }
    }
    private fun launchImageCrop(uri: Uri){
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Notice")
            .setContentText("Apakah Anda Butuh Di Crop Pada Image")
            .setCancelText("No")
            .setConfirmText("Crop")
            .showCancelButton(true)
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
                selectedImageUri = uri
                binding.ImageDetail.setImageURI(selectedImageUri)
            }
            .setConfirmClickListener {sDialog ->
                sDialog.cancel()
                CropImage.activity(uri)
                .start(this)
            }
            .show()
    }
    private fun doUploadImage(){
        val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedImageUri!!,"r",null)?:return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir,contentResolver.getFileName(selectedImageUri!!))
//        val file = File(this.externalCacheDir(),)
//        val file = File(this.getExternalCacheDir(), "cropped_image.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val foto = UploadRequestBody(file,"image",this)
        val image = MultipartBody.Part.createFormData("file",file.name,foto)
//        val image = MultipartBody.Part.createFormData("file","testing",foto)
        presenter?.prediction(image,this)
    }
    private fun ContentResolver.getFileName(fileUri:Uri):String{
        var name = "cropped_image.jpg"
        val returnCursor = this.query(fileUri,null,null,null,null)
        if (returnCursor != null){
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                photoFile = createImageFile()
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    override fun successPredict(pindah: Intent) {
        startActivity(pindah)
    }
    override fun showLoading() {
        binding.loadingPredict.apply {
            visibility = View.VISIBLE
        }
        binding.apply {
            BtnUpload.isEnabled = false
            BtnChooseImage.isEnabled = false
//            BtnCamera.isEnabled = false
        }
    }
    override fun hideLoading() {
        binding.loadingPredict.apply {
            visibility = View.GONE
        }
        binding.apply {
            BtnUpload.isEnabled = true
            BtnChooseImage.isEnabled = true
//            BtnCamera.isEnabled = true
            progressBar2.progress = 0
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar2.progress = percentage
    }
}