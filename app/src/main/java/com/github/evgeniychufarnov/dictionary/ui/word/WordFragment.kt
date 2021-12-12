package com.github.evgeniychufarnov.dictionary.ui.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.databinding.FragmentWordBinding
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WordFragment : Fragment() {
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.word.observe(viewLifecycleOwner) {
            setFields(it)
        }
    }

    private fun setFields(word: WordEntity) {
        binding.wordTextView.text = word.word

        word.meanings.firstOrNull()?.let {
            binding.transcriptionTextView.text = it.transcription

            Glide.with(requireContext())
                .load(it.getFullImageUrl())
                .placeholder(R.drawable.default_image)
                .into(binding.wordImageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}