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

}