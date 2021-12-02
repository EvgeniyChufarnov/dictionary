package com.github.evgeniychufarnov.dictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.evgeniychufarnov.dictionary.R
import com.github.evgeniychufarnov.dictionary.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_layout, SearchFragment())
                .commit()
        }
    }
}