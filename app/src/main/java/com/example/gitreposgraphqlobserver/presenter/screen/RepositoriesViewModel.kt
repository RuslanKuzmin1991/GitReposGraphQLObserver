package com.example.gitreposgraphqlobserver.presenter.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitreposgraphqlobserver.data.entity.PaginatedRepositories
import com.example.gitreposgraphqlobserver.domain.RepositoryProvider
import com.example.gitreposgraphqlobserver.domain.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val provider: RepositoryProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun refreshRepositories(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(
                _uiState.value.copy(
                    items = emptyList(),
                    isRefreshing = false,
                    isLoading = false,
                    cursor = null
                )
            )
            getRepositories(name)
        }
    }

    fun getRepositories(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            provider.getRepositories(name, _uiState.value.cursor).collect { result ->
                when (result) {
                    is ResultWrapper.Failure -> onError(result.error)
                    is ResultWrapper.Loading -> onLoading()
                    is ResultWrapper.Success -> onSuccess(result)
                }
            }
        }
    }

    private suspend fun onSuccess(result: ResultWrapper.Success<PaginatedRepositories, String>) {
        _uiState.emit(
            _uiState.value.copy(
                items = _uiState.value.items + result.data.items,
                isLoading = false,
                isRefreshing = false,
                error = null,
                cursor = result.data.endCursor,
                hasNextPage = result.data.hasNextPage
            )
        )
    }

    private suspend fun onLoading() {
        if (!_uiState.value.isRefreshing) _uiState.emit(
            _uiState.value.copy(
                isLoading = true,
                error = null
            )
        )
    }

    private suspend fun onError(error: String) {
        _uiState.emit(
            _uiState.value.copy(
                error = error,
                isLoading = false,
                isRefreshing = false
            )
        )
    }
}