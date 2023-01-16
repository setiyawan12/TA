package yayang.setiyawan.pneumonia.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import yayang.setiyawan.pneumonia.contract.CameraActivityContract
import yayang.setiyawan.pneumonia.databinding.ActivityCameraBinding
import yayang.setiyawan.pneumonia.presenter.CameraActivityPresenter
import java.io.File

class CameraActivity : AppCompatActivity(),CameraActivityContract.View {
    private var presenter:CameraActivityContract.Presenter?=null
    private var choosedImage:com.esafirm.imagepicker.model.Image?=null
    private var image :MultipartBody.Part?=null
    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CameraActivityPresenter(this)
        binding= ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnChooseListener()
        btnUploadListener()
    }
    private fun btnChooseListener(){
        binding.BtnChooseImage.setOnClickListener {
            chooseImage()
        }
    }
    private fun btnUploadListener(){
        binding.BtnUpload.setOnClickListener {
            if (choosedImage == null){
                SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Please Inset Image")
                    .show()
//                showToast("please insert image")
            }else{
                uploadImage()
            }
        }
    }
    private val imagePickerLauncher = registerImagePicker {
        choosedImage = if (it.isEmpty()) null else it[0]
        showImage()
    }
    private fun chooseImage() {
        val config = ImagePickerConfig{
            mode = ImagePickerMode.SINGLE
            isIncludeVideo= false
        }
        imagePickerLauncher.launch(config)
    }
    private fun showImage(){
        choosedImage?.let {
            image->binding.ImageDetail.setImageURI(image.uri)
        }
    }
    fun uploadImage(){
        if (choosedImage != null){
            val originalFile = File(choosedImage?.path!!)
            val imagePart : RequestBody = originalFile.asRequestBody("image/*".toMediaTypeOrNull())
            image = MultipartBody.Part.createFormData("file",originalFile.name,imagePart)
        }
        presenter?.prediction(image!!,this)
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