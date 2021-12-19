package com.github.evgeniychufarnov.dictionary.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.ui.search.SearchFragment
import com.github.evgeniychufarnov.dictionary.utils.viewById
import com.github.evgeniychufarnov.model.entities.WordEntity
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope

class HistoryFragment : Fragment(), KoinScopeComponent {

    override val scope = getOrCreateScope().value
    private val viewModel = scope.get<HistoryViewModel>()

    private val historyRecyclerView by viewById<RecyclerView>(R.id.history_recycler_view)
    private val noResultsMessageTextView by viewById<TextView>(R.id.history_no_results_message_text_view)

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeValues()
    }

    private fun initRecyclerView() {
        adapter = HistoryAdapter(viewModel::onWordClicked)
        historyRecyclerView.adapter = adapter
        historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeValues() {
        viewModel.history.observe(viewLifecycleOwner) { it ->
            (view as ViewGroup).children.forEach { it.isVisible = false }

            if (it.isNotEmpty()) {
                adapter.setData(it)
                historyRecyclerView.isVisible = true
            } else {
                noResultsMessageTextView.isVisible = true
            }
        }

        viewModel.wordClickedEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                (requireActivity() as SearchFragment.Contract).navigateToWord(it)
                viewModel.onWordClickedEventFinished()
            }
        }
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

    override fun onDestroyView() {
        scope.close()
        super.onDestroyView()
    }
}