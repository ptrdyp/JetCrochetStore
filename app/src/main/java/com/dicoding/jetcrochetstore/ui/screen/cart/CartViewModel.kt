package com.dicoding.jetcrochetstore.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetcrochetstore.data.CrochetRepository
import com.dicoding.jetcrochetstore.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CrochetRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>> get() = _uiState

    fun getAddedOrderCrochet() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderCrochets()
                .collect{ orderCrochet ->
                    val totalPrice = orderCrochet.sumOf {
                        it.crochet.price * it.count
                    }
                    _uiState.value = UiState.Success(CartState(orderCrochet, totalPrice))
                }
        }
    }

    fun updateOrderCrochet(crochetId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderCrochet(crochetId, count)
                .collect{ isUpdated ->
                    getAddedOrderCrochet()
                }
        }
    }
}