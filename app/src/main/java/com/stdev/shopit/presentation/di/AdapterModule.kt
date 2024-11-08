package com.stdev.shopit.presentation.di

import com.stdev.shopit.presentation.adapter.CartAdapter
import com.stdev.shopit.presentation.adapter.HomeAdapter
import com.stdev.shopit.presentation.adapter.SearchAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

//    it means that only one instance of the dependency will be created
//    and shared throughout the application's lifecycle.

    @Singleton
    @Provides
    fun providesHomeAdapter() : HomeAdapter{
        return HomeAdapter()
    }

    @Singleton
    @Provides
    fun providesCartAdapter() : CartAdapter{
        return CartAdapter()
    }

    @Singleton
    @Provides
    fun providesSearchAdapter() : SearchAdapter{
        return SearchAdapter()
    }



}