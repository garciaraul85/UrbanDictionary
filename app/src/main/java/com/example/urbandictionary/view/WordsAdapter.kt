package com.example.urbandictionary.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.R
import com.example.urbandictionary.model.response.Word

class WordsAdapter : RecyclerView.Adapter<WordViewHolder>() {

    private var words: MutableList<Word> = mutableListOf()

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bindItem(words[position])
    }

    fun update(words: MutableList<Word>) {
        this.words.clear()
        this.words = words
        notifyDataSetChanged()
    }

    fun sortByThumbsUp() {
        this.words.sortBy {
            it.thumbs_up
        }
        notifyDataSetChanged()
    }

    fun sortByThumbsDown() {
        this.words.sortBy {
            it.thumbs_down
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.word_item,
                parent,
                false
            )
        )
}