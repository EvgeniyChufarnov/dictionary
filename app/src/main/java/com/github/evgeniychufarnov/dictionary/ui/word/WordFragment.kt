package com.github.evgeniychufarnov.dictionary.ui.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.utils.viewById
import com.github.evgeniychufarnov.model.entities.WordEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WordFragment : Fragment() {
    private val wordTextView by viewById<TextView>(R.id.word_text_view)
    private val transcriptionTextView by viewById<TextView>(R.id.transcription_text_view)
    private val wordImageView by viewById<ImageView>(R.id.word_image_view)

    private val viewModel by viewModel<WordViewModel> {
        parametersOf(arguments?.getParcelable(ARGUMENT_WORD_KEY))
    }

    companion object {
        private const val ARGUMENT_WORD_KEY = "word key"

        fun getInstance(word: WordEntity) = WordFragment().apply {
            arguments = bundleOf(ARGUMENT_WORD_KEY to word)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.word.observe(viewLifecycleOwner) {
            setFields(it)
        }
    }

    private fun setFields(word: WordEntity) {
        wordTextView.text = word.word

        word.meanings.firstOrNull()?.let {
            transcriptionTextView.text = it.transcription

            Glide.with(requireContext())
                .load(it.getFullImageUrl())
                .placeholder(R.drawable.default_image)
                .into(wordImageView)
        }
    }
}