package com.github.evgeniychufarnov.dictionary.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.databinding.ItemWordBinding
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity

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
        private val binding = ItemWordBinding.bind(itemView)

        fun bind(wordEntity: WordEntity, onClick: (WordEntity) -> Unit) {
            binding.wordTextView.text = wordEntity.word

            binding.root.setOnClickListener {
                onClick(wordEntity)
            }
        }
    }
}