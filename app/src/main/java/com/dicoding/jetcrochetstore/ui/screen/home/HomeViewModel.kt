package com.dicoding.jetcrochetstore.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetcrochetstore.data.CrochetRepository
import com.dicoding.jetcrochetstore.model.OrderCrochet
import com.dicoding.jetcrochetstore.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CrochetRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderCrochet>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderCrochet>>> get() = _uiState

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _searchResult: MutableStateFlow<List<OrderCrochet>> = MutableStateFlow(
        emptyList()
    )
    val searchResult: MutableStateFlow<List<OrderCrochet>> get() = _searchResult


    fun getAllCrochet() {
        viewModelScope.launch {
            repository.getAllCrochets()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderCrochets ->
                    _uiState.value = UiState.Success(orderCrochets)
                }
        }
    }

    fun search(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            val crochets = repository.searchCrochet(_query.value)
            _searchResult.value = crochets.map { OrderCrochet(it, 0) }
        }
    }

}