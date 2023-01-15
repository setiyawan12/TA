package yayang.setiyawan.pneumonia

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import yayang.setiyawan.pneumonia.databinding.ActivityMainBinding
import yayang.setiyawan.pneumonia.fragment.AkunFragment
import yayang.setiyawan.pneumonia.fragment.HistoryFragment
import yayang.setiyawan.pneumonia.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import yayang.setiyawan.pneumonia.clasification.Clasification
import yayang.setiyawan.pneumonia.fragment.ArticleFragment
import yayang.setiyawan.pneumonia.ui.CameraActivity


class MainActivity : AppCompatActivity(){
    private lateinit var permissionLauncher :ActivityResultLauncher<Array<String>>
    private var  isReadPermissionGranted = false
    private var  isLocationPermissionGranted = false
    private var  isWritePermissionGranted = false
    private var  isCameraPermissionGranted = false
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            permission ->
            isReadPermissionGranted = permission[Manifest.permission.READ_EXTERNAL_STORAGE]?:isReadPermissionGranted
            isLocationPermissionGranted = permission[Manifest.permission.ACCESS_FINE_LOCATION]?:isLocationPermissionGranted
            isWritePermissionGranted= permission[Manifest.permission.WRITE_EXTERNAL_STORAGE]?:isWritePermissionGranted
            isCameraPermissionGranted = permission[Manifest.permission.CAMERA]?:isCameraPermissionGranted
        }
        fabs()
        requestPermission()
        val homeFragment = HomeFragment()
        val historyFragment = HistoryFragment()
        val akunFragment = AkunFragment()
        val articleFragment = ArticleFragment()
        loadFragment(homeFragment)
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nvHome->{
                    loadFragment(homeFragment)
                }
                R.id.nvHistory->{
                    loadFragment(historyFragment)
                }
                R.id.nvArticle->{
                    loadFragment(articleFragment)
                }
                R.id.nvAkun->{
                    loadFragment(akunFragment)
                }
            }
            true
        }
    }
    private fun fabs(){
        fab.setOnClickListener {
            startActivity(Intent(this,CameraActivity::class.java))
        }
    }
    private fun requestPermission(){
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
        isWritePermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED

        val permissionRequest:MutableList<String> =ArrayList()
        if (!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!isWritePermissionGranted){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!isCameraPermissionGranted){
            permissionRequest.add(Manifest.permission.CAMERA)
        }
        if (permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment,fragment)
        transaction.commit()
    }
}