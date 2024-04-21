package com.trungcs.androidnetwork.data.repo

import com.trungcs.androidnetwork.remote.model.Hello
import com.trungcs.androidnetwork.utils.Result

interface SampleRepository {
    suspend fun getHello(): Result<Hello, Throwable>
}