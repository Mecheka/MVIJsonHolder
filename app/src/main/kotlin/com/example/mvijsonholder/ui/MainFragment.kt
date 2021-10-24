package com.example.mvijsonholder.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvijsonholder.common.BaseFragment
import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.databinding.FragmentMainBinding
import com.example.mvijsonholder.ui.adapter.PostAdapter
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val viewModel by viewModel<MainViewModel>()
    private val postAdapter by lazy {
        PostAdapter()
    }

    override fun setupBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onTrackingEvent(MainIntent.GetPosts)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerPost.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = postAdapter
        }

        lifecycleScope.launchWhenStarted {
            viewModel.posts.collect { state ->
                when (state) {
                    is DataState.Empty -> {
                        binding.progressBar.isVisible = true
                        postAdapter.submitList(emptyList())
                        showDialog(message = "Empty list")
                    }
                    is DataState.Error -> {
                        binding.progressBar.isVisible = false
                        showDialog(message = state.message)
                    }
                    is DataState.Loading -> {
                        postAdapter.submitList(emptyList())
                        binding.progressBar.isVisible = true
                    }
                    is DataState.Success -> {
                        binding.progressBar.isVisible = false
                        postAdapter.submitList(state.data)
                    }
                }
            }
        }
    }

    private fun showDialog(title: String = "Warring", message: String?) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setPositiveButton("OK") {dialog, _ ->
                dialog.dismiss()
            }

        message?.let {
            alertDialog.setMessage(it)
        }

        alertDialog.create().show()
    }
}
