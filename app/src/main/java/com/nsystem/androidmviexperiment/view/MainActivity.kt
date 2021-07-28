package com.nsystem.androidmviexperiment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.nsystem.androidmviexperiment.databinding.ActivityMainBinding
import com.nsystem.androidmviexperiment.mvi.ChampionMviIntent
import com.nsystem.androidmviexperiment.mvi.LoadState
import com.nsystem.androidmviexperiment.viewmodel.MainViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val recyclerAdapter = ItemAdapter {
        viewModel.onIntent(ChampionMviIntent.Search(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeState()
        initView()
    }

    private fun observeState() {
        viewModel
            .state
            .onEach {
                binding.state = it
                if (it.loadState == LoadState.LOADING) binding.acetSearch.text?.clear()
                if (it.errorMessage.isNotBlank()) showErrorMessage(it.errorMessage)
            }
            .launchIn(lifecycleScope)
    }

    private fun initView() {
        binding.rvChampion.adapter = recyclerAdapter
        binding.acetSearch.doAfterTextChanged {
            viewModel.onIntent(ChampionMviIntent.LoadSuggestion(it.toString()))
            binding.btnSubmit.isEnabled = it?.isNotBlank() == true
        }
        binding.btnSubmit.setOnClickListener {
            viewModel.onIntent(ChampionMviIntent.Search(binding.acetSearch.text.toString()))
        }
    }

    private fun showErrorMessage(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}