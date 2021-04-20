package com.mihailchistousov.leroymerlin.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.mihailchistousov.leroymerlin.R
import com.mihailchistousov.leroymerlin.utils.hideKeyboard
import com.mihailchistousov.leroymerlin.utils.showKeyboard
import kotlinx.android.synthetic.main.search.*

class SearchFragment: Fragment(R.layout.search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.getBoolean("addShared", false) == true)
            sharedElementReturnTransition =  TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSearch.requestFocus()
        context?.showKeyboard()
        back.setOnClickListener { requireActivity().hideKeyboard(); findNavController().popBackStack() }
    }
}