package com.github.evgeniychufarnov.dictionary.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.github.evgeniychufarnov.dictionary.databinding.DialogFragmentSearchWorldBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchLocalWordDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogFragmentSearchWorldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentSearchWorldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchLocalWordSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    (parentFragment as Contract).searchWord(it)
                }
                dismiss()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (parentFragment !is Contract) {
            throw RuntimeException("ParentFragment must implement SearchLocalWordDialogFragment.Contract")
        }
    }

    interface Contract {
        fun searchWord(word: String)
    }
}