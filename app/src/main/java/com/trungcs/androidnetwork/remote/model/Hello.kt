package com.trungcs.androidnetwork.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Hello(
    @SerializedName("hello")
    @Expose
    val hello: String,
)