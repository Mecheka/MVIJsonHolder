package com.example.mvijsonholder.ui

import android.os.Bundle
import android.view.View
import com.example.mvijsonholder.common.BaseFragment
import com.example.mvijsonholder.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun setupBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
