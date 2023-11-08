package com.dicoding.jetcrochetstore.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetcrochetstore.R
import com.dicoding.jetcrochetstore.di.Injection
import com.dicoding.jetcrochetstore.ui.ViewModelFactory
import com.dicoding.jetcrochetstore.ui.common.UiState
import com.dicoding.jetcrochetstore.ui.components.CartItem
import com.dicoding.jetcrochetstore.ui.components.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderCrochet()
            }

            is UiState.Success -> {
                CartContent(
                    state = uiState.data,
                    onProductCountChanged = { crochetId, count ->
                        viewModel.updateOrderCrochet(crochetId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage =
        stringResource(R.string.share_message, state.orderCrochet.count(), state.totalPrice)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.keranjang),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(state.orderCrochet, key = { it.crochet.id }) {
                CartItem(
                    crochetId = it.crochet.id,
                    image = it.crochet.image,
                    title = it.crochet.title,
                    totalPrice = it.crochet.price * it.count,
                    count = it.count,
                    onProductCountChange = onProductCountChanged,
                )
                Divider()
            }
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.orderCrochet.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}