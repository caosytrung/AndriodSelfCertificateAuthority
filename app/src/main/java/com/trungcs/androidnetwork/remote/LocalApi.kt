package com.trungcs.androidnetwork.remote

import com.google.gson.GsonBuilder
import com.trungcs.androidnetwork.remote.model.Hello
import retrofit2.http.GET

interface LocalApi {
    @GET(Endpoint.GET_HELLO)
    suspend fun getHello(): Hello

    companion object {
        internal val DEFAULT_GSON = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
}