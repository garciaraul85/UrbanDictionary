package com.example.urbandictionary.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.R
import com.example.urbandictionary.model.network.UrbanRepositoryImpl
import com.example.urbandictionary.model.network.WebService
import com.example.urbandictionary.model.response.Word
import com.example.urbandictionary.viewmodel.UrbanViewModel
import com.example.urbandictionary.viewmodel.UrbanViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: UrbanViewModel
    var wordsAdapter: WordsAdapter = WordsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            UrbanViewModelFactory(UrbanRepositoryImpl(WebService.instance))
        ).get(UrbanViewModel::class.java)

        viewModel.stateLiveData.observe(this, Observer { appState ->
            when (appState) {
                is UrbanViewModel.AppState.LOADING -> displayLoading()
                is UrbanViewModel.AppState.SUCCESS -> displayWords(appState.wordsList)
                is UrbanViewModel.AppState.ERROR -> displayMessage(appState.message)
                else -> displayMessage("Something Went Wrong... Try Again.")
            }
        })

        initRecyclerView()
        wordSearch()
    }

    private fun displayWords(wordsList: MutableList<Word>) {
        // set recycler to eliminate flicker
        wordsAdapter.update(wordsList)

        // set correct visible element
        progressBar.visibility = View.GONE
        rvNews.visibility = View.VISIBLE
        messageText.visibility = View.GONE
    }

    private fun displayLoading() {
        // set correct visible element
        progressBar.visibility = View.VISIBLE
        rvNews.visibility = View.GONE
        messageText.visibility = View.GONE
    }

    private fun displayMessage(message: String) {
        // set correct visible element
        progressBar.visibility = View.GONE
        rvNews.visibility = View.GONE
        messageText.visibility = View.VISIBLE
        //set message
        messageText.text = message
    }

    private fun initRecyclerView() {
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = wordsAdapter
    }

    private fun wordSearch() {
        viewModel.searchDefinitions(word_search)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_thumbsUp -> {
                wordsAdapter.sortByThumbsUp()
                true
            }
            R.id.sort_thumbsDown ->  {
                wordsAdapter.sortByThumbsDown()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}
