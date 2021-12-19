package com.github.evgeniychufarnov.dictionary.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.utils.viewById
import com.github.evgeniychufarnov.model.entities.WordEntity

class WordsAdapter(
    private val onClick: (WordEntity) -> Unit
) : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    private var data: List<WordEntity> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<WordEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(data[position], onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class WordViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_word, viewGroup, false)
    ) {
        private val wordTextView by viewById<TextView>(R.id.word_text_view)

        fun bind(wordEntity: WordEntity, onClick: (WordEntity) -> Unit) {
            wordTextView.text = wordEntity.word

            itemView.setOnClickListener {
                onClick(wordEntity)
            }
        }
    }
}