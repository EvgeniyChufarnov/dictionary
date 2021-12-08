package com.github.evgeniychufarnov.dictionary.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.evgeniychufarnov.dictionary.databinding.FragmentSearchBinding
import com.github.evgeniychufarnov.dictionary.di.app
import com.github.evgeniychufarnov.dictionary.domain.ScreenState
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import javax.inject.Inject

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel

    private lateinit var adapter: WordsAdapter

    override fun onAttach(context: Context) {
        app.appComponent.injectSearchFragment(this)
        super.onAttach(context)
    }

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

        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        initRecyclerView()
        setListener()
        observeState()
    }

    private fun initRecyclerView() {
        adapter = WordsAdapter()
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

    private fun observeState() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            renderState(it)
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
        val imm = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        imm?.hideSoftInputFromWindow(binding.searchWordSearchView.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}