package com.miles.cosmicadventuredeck

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.miles.cosmicadventuredeck.data.CardRepositoryImplementation
import com.miles.cosmicadventuredeck.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }

        fetchAndPopulateData()
    }

    fun fetchAndPopulateData() {
        val repository: CardRepositoryImplementation = GlobalContext.get().get()
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.fetchAllCardsFromSupabase()
            repository.insertAllCards(data)
        }
    }
}