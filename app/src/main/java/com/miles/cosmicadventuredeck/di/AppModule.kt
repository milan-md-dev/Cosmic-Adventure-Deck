package com.miles.cosmicadventuredeck.di


import android.content.Context
import androidx.room.Room
import com.miles.cosmicadventuredeck.BuildConfig
import com.miles.cosmicadventuredeck.data.CardDatabase
import com.miles.cosmicadventuredeck.data.CardRepositoryImplementation
import com.miles.cosmicadventuredeck.ui.MainViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideDatabase(context: Context): CardDatabase =
    Room.databaseBuilder(
        context,
        CardDatabase::class.java,
        "CardDatabase"
    )
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(cardDatabase: CardDatabase) = cardDatabase.cardDao()

fun provideSupabaseClient() = createSupabaseClient(
    supabaseUrl = BuildConfig.SUPABASE_URL,
    supabaseKey = BuildConfig.SUPABASE_ANON_KEY
) {
    install(Postgrest)
    install(Storage)
}

fun providePostgrest(client: SupabaseClient): Postgrest {
    return client.postgrest
}


val appModule = module {

    // Database
    single {
        provideDatabase(androidContext())
    }

    single {
        provideDao(get())
    }

    single {
        provideSupabaseClient()
    }

    single {
        providePostgrest(get())
    }

    // Repository
    factory {
        CardRepositoryImplementation(get(), get())
    }

    // ViewModels
    viewModel {
        MainViewModel(get())
    }

}

