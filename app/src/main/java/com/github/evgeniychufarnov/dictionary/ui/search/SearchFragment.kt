package com.github.evgeniychufarnov.dictionary.ui.search

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.databinding.FragmentSearchBinding
import com.github.evgeniychufarnov.dictionary.domain.ScreenState
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), SearchLocalWordDialogFragment.Contract {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()

    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.wordsRecyclerView.adapter = adapter
        binding.wordsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setListener() {
        binding.searchWordSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchClicked(binding.searchWordSearchView.query.toString())
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
        binding.root.children.filterNot { it.id == binding.searchWordSearchView.id }
            .forEach { it.isVisible = false }

        when (state) {
            is ScreenState.Success -> {
                if (state.value.isNotEmpty()) {
                    adapter.setData(state.value)
                    binding.wordsRecyclerView.isVisible = true
                } else {
                    binding.noResultsMessageTextView.isVisible = true
                }
            }
            is ScreenState.Error -> {
                binding.errorMessageTextView.isVisible = true
            }
            is ScreenState.Loading -> {
                binding.loadingLayout.isVisible = true
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as? InputMethodManager

        imm?.hideSoftInputFromWindow(binding.searchWordSearchView.windowToken, 0)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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