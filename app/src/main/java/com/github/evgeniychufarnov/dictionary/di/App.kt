package com.github.evgeniychufarnov.dictionary.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.github.evgeniychufarnov.dictionary.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        RepoModule::class,
        ViewModelFactoryModule::class
    ]
)
@Singleton
interface ApplicationComponent {
    fun injectSearchFragment(fragment: SearchFragment)
}

class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}

val Fragment.app: App
    get() = requireActivity().application as App