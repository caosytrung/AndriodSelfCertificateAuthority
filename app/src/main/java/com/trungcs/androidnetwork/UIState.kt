package com.trungcs.androidnetwork

import com.trungcs.androidnetwork.remote.model.Hello

sealed class UiState {
    data object Loading : UiState()
    data object Error : UiState()
    data class Success(val hello: Hello) :
        UiState()
}