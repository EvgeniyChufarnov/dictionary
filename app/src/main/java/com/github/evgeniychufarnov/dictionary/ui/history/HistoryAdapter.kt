package com.github.evgeniychufarnov.dictionary.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.utils.viewById

class HistoryAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var data: List<String> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<String>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position], onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class HistoryViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_word, viewGroup, false)
    ) {
        private val wordTextView by viewById<TextView>(R.id.word_text_view)

        fun bind(word: String, onClick: (String) -> Unit) {
            wordTextView.text = word

            itemView.setOnClickListener {
                onClick(word)
            }
        }
    }
}