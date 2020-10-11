package com.nsystem.androidmviexperiment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.nsystem.androidmviexperiment.R
import com.nsystem.androidmviexperiment.contract.mvi.Action
import com.nsystem.androidmviexperiment.contract.mvi.MviView
import com.nsystem.androidmviexperiment.mvi.state.UiState
import com.nsystem.androidmviexperiment.utils.lazyUi
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MviView<Action, UiState> {

    private val actionsObservable by lazyUi {

        val clicks = submitBtn.clicks().map {
            UiAction.SearchAction(searchView.text.toString())
        }

        val suggestions = suggestionPicks.map {
            UiAction.SearchAction(it)
        }

        val textChanges = searchView.textChanges().skipInitialValue().map {
            UiAction.LoadSuggestionsAction(it.toString())
        }

        Observable.merge(clicks, suggestions, textChanges)
    }

    private val suggestionPicks = BehaviorSubject.create<String>()

    private val viewModel: MainViewModel by viewModels()

    private val recyclerAdapter = ItemAdapter {
        suggestionPicks.onNext(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.bind(this)
    }

    override fun render(state: UiState) {
        submitBtn.isEnabled = !state.loading
        progressView.visibility = if (state.loading) View.VISIBLE else View.GONE
        tvName.text = null
        tvLane.text = null
        state.data?.let {
            tvName.text = it.name
            tvLane.text = it.lane
        }
        recyclerAdapter.replaceWith(state.suggestions ?: emptyList())

        state.error?.let {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override val actions: Observable<Action>
        get() = actionsObservable as Observable<Action>

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unbind()
    }
}