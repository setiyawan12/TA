package com.zahro.pneumonia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_article.*
import com.zahro.pneumonia.R

class ArticleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_article,container,false)
        return  view
//        return inflater.inflate(R.layout.fragment_article, container, false)
    }
}