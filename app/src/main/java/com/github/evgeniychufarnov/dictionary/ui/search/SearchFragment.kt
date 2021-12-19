package com.github.evgeniychufarnov.dictionary.ui.search

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.utils.viewById
import com.github.evgeniychufarnov.model.ScreenState
import com.github.evgeniychufarnov.model.entities.WordEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), SearchLocalWordDialogFragment.Contract {
    private val viewModel by viewModel<SearchViewModel>()

    private val wordsRecyclerView by viewById<RecyclerView>(R.id.words_recycler_view)
    private val searchWordSearchView by viewById<SearchView>(R.id.search_word_search_view)
    private val noResultsMessageTextView by viewById<TextView>(R.id.word_no_results_message_text_view)
    private val errorMessageTextView by viewById<TextView>(R.id.error_message_text_view)
    private val loadingLayout by viewById<FrameLayout>(R.id.loading_layout)

    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initRecyclerView()
        setListener()
        observeValues()
    }

    private fun initRecyclerView() {
        adapter = WordsAdapter(viewModel::onWordClicked)
        wordsRecyclerView.adapter = adapter
        wordsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setListener() {
        searchWordSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchClicked(searchWordSearchView.query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun observeValues() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            renderState(it)
        }

        viewModel.wordClickedEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                (requireActivity() as Contract).navigateToWord(it)
                viewModel.onWordClickedEventFinished()
            }
        }

        viewModel.searchLocalWordEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                (requireActivity() as Contract).navigateToWord(it)
                viewModel.onSearchLocalWordEventFinished()
            }
        }

        viewModel.localWordNotFoundEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.local_word_not_found_message),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.onLocalWordNotFoundEventFinished()
            }
        }
    }

    private fun renderState(state: ScreenState<List<WordEntity>>) {
        (view as ViewGroup).children.filterNot { it.id == searchWordSearchView.id }
            .forEach { it.isVisible = false }

        when (state) {
            is ScreenState.Success -> {
                if (state.value.isNotEmpty()) {
                    adapter.setData(state.value)
                    wordsRecyclerView.isVisible = true
                } else {
                    noResultsMessageTextView.isVisible = true
                }
            }
            is ScreenState.Error -> {
                errorMessageTextView.isVisible = true
            }
            is ScreenState.Loading -> {
                loadingLayout.isVisible = true
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as? InputMethodManager

        imm?.hideSoftInputFromWindow(searchWordSearchView.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_history -> {
                (requireActivity() as Contract).navigateToHistory()
                true
            }
            R.id.item_search_local -> {
                showDialogFragment()
                true
            }
            else -> { false }
        }
    }

    private fun showDialogFragment() {
        SearchLocalWordDialogFragment().show(childFragmentManager, null)
    }

    override fun searchWord(word: String) {
        viewModel.onSearchLocalWord(word)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (requireActivity() !is Contract) {
            throw RuntimeException("Activity must implement SearchFragment.Contract")
        }
    }

    interface Contract {
        fun navigateToWord(word: WordEntity)
        fun navigateToHistory()
    }
}