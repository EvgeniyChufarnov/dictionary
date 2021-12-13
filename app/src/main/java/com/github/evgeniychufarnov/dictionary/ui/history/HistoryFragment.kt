package com.github.evgeniychufarnov.dictionary.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.evgeniychufarnov.dictionary.databinding.FragmentHistoryBinding
import com.github.evgeniychufarnov.model.entities.WordEntity
import com.github.evgeniychufarnov.dictionary.ui.search.SearchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HistoryViewModel>()

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeValues()
    }

    private fun initRecyclerView() {
        adapter = HistoryAdapter(viewModel::onWordClicked)
        binding.historyRecyclerView.adapter = adapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeValues() {
        viewModel.history.observe(viewLifecycleOwner) { it ->
            binding.root.children.forEach { it.isVisible = false }

            if (it.isNotEmpty()) {
                adapter.setData(it)
                binding.historyRecyclerView.isVisible = true
            } else {
                binding.noResultsMessageTextView.isVisible = true
            }
        }

        viewModel.wordClickedEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                (requireActivity() as SearchFragment.Contract).navigateToWord(it)
                viewModel.onWordClickedEventFinished()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (requireActivity() !is SearchFragment.Contract) {
            throw RuntimeException("Activity must implement HistoryFragment.Contract")
        }
    }

    interface Contract {
        fun navigateToWord(word: WordEntity)
    }
}