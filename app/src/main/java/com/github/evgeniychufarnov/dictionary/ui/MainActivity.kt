package com.github.evgeniychufarnov.dictionary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import com.github.evgeniychufarnov.dictionary.ui.history.HistoryFragment
import com.github.evgeniychufarnov.dictionary.ui.search.SearchFragment
import com.github.evgeniychufarnov.dictionary.ui.word.WordFragment

class MainActivity : AppCompatActivity(), SearchFragment.Contract, HistoryFragment.Contract {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_layout, SearchFragment())
                .commit()
        }
    }

    override fun navigateToWord(word: WordEntity) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_layout, WordFragment.getInstance(word))
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToHistory() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_layout, HistoryFragment())
            .addToBackStack(null)
            .commit()
    }
}