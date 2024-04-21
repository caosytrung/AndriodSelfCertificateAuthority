package com.trungcs.androidnetwork.di

import com.trungcs.androidnetwork.data.repo.SampleRepository
import com.trungcs.androidnetwork.data.repo.SampleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindHelloRepository(
        productServiceImpl: SampleRepositoryImpl,
    ): SampleRepository
}