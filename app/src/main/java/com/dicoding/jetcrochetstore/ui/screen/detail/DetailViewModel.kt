package com.dicoding.jetcrochetstore.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetcrochetstore.data.CrochetRepository
import com.dicoding.jetcrochetstore.model.Crochet
import com.dicoding.jetcrochetstore.model.OrderCrochet
import com.dicoding.jetcrochetstore.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CrochetRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderCrochet>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderCrochet>> get() = _uiState

    fun getCrochetById(crochetId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderCrochetById(crochetId))
        }
    }

    fun addToCart(crochet: Crochet, count: Int) {
        viewModelScope.launch {
            repository.updateOrderCrochet(crochet.id, count)
        }
    }
}