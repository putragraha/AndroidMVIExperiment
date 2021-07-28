package com.nsystem.androidmviexperiment.view

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nsystem.androidmviexperiment.mvi.LoadState

object ActivityMainView {

    @BindingAdapter("loadingVisible")
    @JvmStatic
    fun isLoadingVisible(view: ProgressBar, loadState: LoadState) {
        view.isVisible = loadState == LoadState.LOADING
    }

    @BindingAdapter("data")
    @JvmStatic
    fun updateData(view: RecyclerView, championNames: List<String>) {
        (view.adapter as ItemAdapter).replaceWith(championNames)
    }
}