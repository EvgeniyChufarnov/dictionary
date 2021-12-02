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
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.evgeniychufarnov.dictionary.app
import com.github.evgeniychufarnov.dictionary.databinding.FragmentSearchBinding
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity

class SearchFragment : Fragment(), SearchContract.View {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: SearchContract.Presenter

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

        presenter = app.getSearchPresenter()
        presenter.attachView(this)

        initRecyclerView()
        setListener()
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
                presenter.onSearchClicked(binding.searchWordSearchView.query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
    }

    override fun showWords(words: List<WordEntity>) {
        adapter.setData(words)
    }

    override fun changeState(state: SearchContract.ScreenState) {
        binding.root.children.filterNot { it.id == binding.searchWordSearchView.id }
            .forEach { it.isVisible = false }

        when (state) {
            SearchContract.ScreenState.SUCCESS -> {
                binding.wordsRecyclerView.isVisible = true
            }
            SearchContract.ScreenState.ERROR -> {
                binding.errorMessageTextView.isVisible = true
            }
            SearchContract.ScreenState.LOADING -> {
                binding.loadingLayout.isVisible = true
                hideKeyboard()
            }
            SearchContract.ScreenState.NOTHING_TO_SHOW -> {
                binding.noResultsMessageTextView.isVisible = true
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        imm?.hideSoftInputFromWindow(binding.searchWordSearchView.windowToken, 0)
    }
}