package com.almarpa.rickandmortyapp.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        // single<RickMortyDatabase> { getDatabase() }
    }
}