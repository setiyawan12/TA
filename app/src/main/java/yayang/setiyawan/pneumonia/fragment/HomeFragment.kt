package yayang.setiyawan.pneumonia.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yayang.setiyawan.pneumonia.R

class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_home,container,false)
        init(view)
        return view
    }
    private fun init(view: View){

    }
}