package com.trungcs.androidnetwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungcs.androidnetwork.data.repo.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sampleRepository: SampleRepository,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getHelloFromLocalServer()
    }

    fun getHelloFromLocalServer() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            val result = sampleRepository.getHello()
            if (result.isFailure()) {
                _uiState.emit(UiState.Error)
            } else {
                _uiState.emit(UiState.Success(result.get()))
            }
        }
    }
}