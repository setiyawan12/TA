package yayang.setiyawan.pneumonia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_history.*
import kotlinx.android.synthetic.main.activity_prediction.*
import kotlinx.android.synthetic.main.activity_prediction.sheet
import yayang.setiyawan.pneumonia.R
import yayang.setiyawan.pneumonia.databinding.ActivityDetailHistoryBinding
import yayang.setiyawan.pneumonia.model.History

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    lateinit var history: History
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight=200
            this.state = BottomSheetBehavior.STATE_COLLAPSED

        }
        setContentView(binding.root)
        setValue()
    }
    fun setValue(){
        val data = intent.getStringExtra("extra")
        history = Gson().fromJson<History>(data,History::class.java)
        val img = history.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(binding.ivResult)
        binding.apply {
            tvUmurPasien.text = history.umur
            tvNoHpPasien.text = history.no_hp
            tvAlamatPasien.text = history.alamat
            tvConfidence.text = history.indicated
            tvPublish.text = history.created_at
            tvNama.text =history.name
        }
    }
}