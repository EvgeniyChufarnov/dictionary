package com.github.evgeniychufarnov.dictionary.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.databinding.ItemWordBinding

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
        private val binding = ItemWordBinding.bind(itemView)

        fun bind(word: String, onClick: (String) -> Unit) {
            binding.wordTextView.text = word

            binding.root.setOnClickListener {
                onClick(word)
            }
        }
    }
}