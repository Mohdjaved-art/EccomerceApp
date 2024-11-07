package com.stdev.shopit.presentation.di

import com.stdev.shopit.data.repository.ShopRepositoryImpl
import com.stdev.shopit.data.repository.datasource.ShopLocalDataSource
import com.stdev.shopit.data.repository.datasource.ShopRemoteDataSource
import com.stdev.shopit.domain.repository.ShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
//    this annotation indicates that the class is a Dagger module. Modules
//    are used to define how dependencies are provided.

    @Singleton
    @Provides
    fun providesShopRepository(shopRemoteDataSource: ShopRemoteDataSource,localDataSource: ShopLocalDataSource) : ShopRepository{
        return ShopRepositoryImpl(shopRemoteDataSource,localDataSource)
    }

//    @InstallIn(SingletonComponent::class): This annotation specifies that the module will be installed
//    in the SingletonComponent. The SingletonComponent means the provided dependencies will have a singleton scope, meaning
//    only one instance of the provided dependency will exist throughout the application lifecycle.

}