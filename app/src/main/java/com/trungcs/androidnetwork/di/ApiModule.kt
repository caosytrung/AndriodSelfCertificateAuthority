package com.trungcs.androidnetwork.di

import com.trungcs.androidnetwork.remote.Endpoint.BASE_URL
import com.trungcs.androidnetwork.remote.LocalApi
import com.trungcs.androidnetwork.remote.LocalApi.Companion.DEFAULT_GSON
import com.trungcs.androidnetwork.utils.apiBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @LocalApiAnnotation
    fun provideLocalAPI(): LocalApi = apiBuilder(LocalApi::class.java) {
        baseUrl = BASE_URL

        converter {
            factory = GsonConverterFactory.create(DEFAULT_GSON)
        }
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LocalApiAnnotation