package com.kevin.newsapp.ui.main.headline.children.science

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kevin.newsapp.R

class ScienceFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.headline_child_fragment_science, container, false)
    }

    companion object {
        fun newInstance(): ScienceFragment = ScienceFragment()
    }
}
