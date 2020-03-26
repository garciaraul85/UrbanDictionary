package com.example.urbandictionary.viewmodel

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionary.model.network.UrbanRepository
import com.example.urbandictionary.model.response.Word
import com.example.urbandictionary.view.IdlingResourceHelper
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import io.reactivex.disposables.CompositeDisposable
import rx.Notification
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class UrbanViewModel constructor(private val urbanRepository: UrbanRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val stateMutableLiveData = MutableLiveData<AppState>()
    val stateLiveData: LiveData<AppState>
        get() = stateMutableLiveData
    var loaded = false

    private fun getDefinitions(term: String) {
        stateMutableLiveData.value = AppState.LOADING
        IdlingResourceHelper.startProcess()
        disposable.add(
            urbanRepository.getDefinitionList(term)
                .subscribe({
                    loaded = true
                    IdlingResourceHelper.endProcess()
                    if (it.list.isEmpty()) {
                        stateMutableLiveData.value = AppState.ERROR("No Definitions Retrieved")
                    } else {
                        stateMutableLiveData.value = AppState.SUCCESS(it.list)
                    }
                }, {
                    loaded = true
                    IdlingResourceHelper.endProcess()
                    //errors
                    val errorString = when (it) {
                        is UnknownHostException -> "No Internet Connection"
                        else -> it.localizedMessage
                    }
                    stateMutableLiveData.value = AppState.ERROR(errorString)
                })
        )
    }

    fun searchDefinitions(searchView: SearchView) {
        RxSearchView.queryTextChanges(searchView)
            .doOnEach { notification: Notification<in CharSequence?> ->
                val query = notification.value as CharSequence?
                if (query != null && query.length > 4) {
                    getDefinitions(query.toString())
                }
            }
            .debounce(
                300,
                TimeUnit.MILLISECONDS
            ) // to skip intermediate letters
            .retry(3)
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    sealed class AppState {
        object LOADING : AppState()
        data class SUCCESS(val wordsList: MutableList<Word>) : AppState()
        data class ERROR(val message: String) : AppState()
    }
}