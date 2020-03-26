package com.example.urbandictionary.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.model.response.Word
import kotlinx.android.synthetic.main.word_item.view.*

class WordViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(word: Word) {
        //populate text
        itemView.definition.text = word.definition
        itemView.uptxt.text = word.thumbs_up.toString()
        itemView.downtxt.text = word.thumbs_down.toString()
    }
}