package com.example.gitreposgraphqlobserver.presenter.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitreposgraphqlobserver.data.entity.PaginatedRepositories
import com.example.gitreposgraphqlobserver.domain.GetRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val useCase: GetRepoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun refreshRepositories(name: String) {
        viewModelScope.launch {
            _uiState.update { currentValue ->
                currentValue.copy(
                    items = emptyList(),
                    isLoading = false,
                    cursor = null
                )
            }

            getRepositories(name)
        }
    }

    fun getRepositories(name: String) {
        viewModelScope.launch {
            _uiState.update { currentValue ->
                currentValue.copy(
                    isLoading = true,
                    error = null
                )
            }
            val result = useCase.invoke(name, _uiState.value.cursor)
            if (result.isSuccess) {
                showData(result.getOrNull())
            } else {
                handle(result.exceptionOrNull()?.message ?: "Error")
            }
        }
    }

    private suspend fun showData(data: PaginatedRepositories?) {
        _uiState.emit(
            _uiState.value.copy(
                items = _uiState.value.items + (data?.items ?: emptyList()),
                isLoading = false,
                error = null,
                cursor = data?.endCursor,
                hasNextPage = data?.hasNextPage ?: false,
            )
        )
    }

    private suspend fun handle(error: String) {
        _uiState.emit(
            _uiState.value.copy(
                error = error,
                isLoading = false
            )
        )
    }
}