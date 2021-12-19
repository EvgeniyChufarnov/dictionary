package com.github.evgeniychufarnov.dictionary.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.utils.viewById
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchLocalWordDialogFragment : BottomSheetDialogFragment() {
    private val searchLocalWordSearchView by viewById<SearchView>(R.id.search_local_word_search_view)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_fragment_search_world, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchLocalWordSearchView.setOnQueryTextListener(object :
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