package com.kevin.newsapp.ui.main.fragment.headline.childfragment.health

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kevin.newsapp.R

class HealthFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.headline_child_fragment_health, container, false)
    }

    companion object {
        fun newInstance(): HealthFragment = HealthFragment()
    }
}
