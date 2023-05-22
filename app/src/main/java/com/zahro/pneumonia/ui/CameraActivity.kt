package com.zahro.pneumonia.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import cn.pedant.SweetAlert.SweetAlertDialog
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.soundcloud.android.crop.Crop
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.zahro.pneumonia.MainActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

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
import kotlin.random.Random.Default.nextInt

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
//        takePicture()
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
//    private fun takePicture(){
//        binding.BtnCamera.setOnClickListener {
//            dispatchTakePictureIntent()
////            CropImage.activity().start(this)
//        }
//    }
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
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        CropImage.activity().start(this)
//        startActivityForResult(intent,100)
//        deleteImageCache(this,selectedImageUri.toString())
//        selectedImageUri = null
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode == Activity.RESULT_OK && requestCode ==GALLERY_REQUEST_CODE ){
//            val imageUri = data?.data
////            val cekUri =File(this.getExternalCacheDir(), "cropped_image.jpg")
////            Toast.makeText(this, cekUri.toString(), Toast.LENGTH_SHORT).show()
////            deleteImageCache(this,cekUri.toUri().toString()
////            Toast.makeText(this, randomString+".jpg", Toast.LENGTH_SHORT).show()
//            Crop.of(imageUri, Uri.fromFile(File(this.getExternalCacheDir(), "image.jpg")))
//                .start(this)
//            val file = File(this.getExternalCacheDir(), "cropped_image.jpg")
//            selectedImageUri = file.toUri()
//            Toast.makeText(this, selectedImageUri.toString(), Toast.LENGTH_SHORT).show()
//            binding.ImageDetail.setImageURI(selectedImageUri)
////            selectedImageUri=imageUri
////            binding.ImageDetail.setImageURI(imageUri)
//        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            val result :CropImage.ActivityResult = CropImage.getActivityResult(data)
////            Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
//            if (resultCode == RESULT_OK){
//                selectedImageUri = result.uri
//                Toast.makeText(this, selectedImageUri.toString(), Toast.LENGTH_SHORT).show()
//                binding.ImageDetail.setImageURI(selectedImageUri)
//            }
//        }
//        if (resultCode == Activity.RESULT_OK && requestCode==100){
//            selectedImageUri = data?.data
//            Toast.makeText(this, selectedImageUri.toString(), Toast.LENGTH_SHORT).show()
//            binding.ImageDetail.setImageURI(selectedImageUri)
//        }
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
//            val uri = FileProvider.getUriForFile(applicationContext,"com.example.android.fileprovider",photoFile)
//            selectedImageUri =uri
//            binding.ImageDetail.setImageURI(selectedImageUri)
//        }
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
//                val  result = CropImage.getActivityResult(data)
//                val result :CropImage.ActivityResult = CropImage.getActivityResult(data)
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK){
                    selectedImageUri = result.uri
                    binding.ImageDetail.setImageURI(selectedImageUri)
                }
            }
        }
    }
    private fun launchImageCrop(uri: Uri){
        CropImage.activity(uri)
//            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
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