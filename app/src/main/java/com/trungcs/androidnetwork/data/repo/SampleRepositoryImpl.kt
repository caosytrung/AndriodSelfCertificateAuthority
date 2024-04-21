package com.trungcs.androidnetwork.data.repo

import com.trungcs.androidnetwork.di.LocalApiAnnotation
import com.trungcs.androidnetwork.remote.LocalApi
import com.trungcs.androidnetwork.utils.result
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    @LocalApiAnnotation val localApi: LocalApi,
) : SampleRepository {
    override suspend fun getHello() = result {
        localApi.getHello()
    }
}