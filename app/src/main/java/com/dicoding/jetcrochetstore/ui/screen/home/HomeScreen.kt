package com.dicoding.jetcrochetstore.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetcrochetstore.di.Injection
import com.dicoding.jetcrochetstore.model.OrderCrochet
import com.dicoding.jetcrochetstore.ui.ViewModelFactory
import com.dicoding.jetcrochetstore.ui.common.UiState
import com.dicoding.jetcrochetstore.ui.components.CrochetItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCrochet()
            }

            is UiState.Success -> {
                HomeContent(
                    orderCrochet = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    orderCrochet: List<OrderCrochet>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {

    Box(modifier = modifier) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
//            stickyHeader {
//                Search(
//                    query = query,
//                    onQueryChange = viewModel::onQueryChanged,
//                    modifier = modifier
//                        .background(MaterialTheme.colorScheme.background)
//                )
//            }
            items(orderCrochet) { data ->
                CrochetItem(
                    image = data.crochet.image,
                    title = data.crochet.title,
                    category = data.crochet.category,
                    price = data.crochet.price,
                    description = data.crochet.description,
                    modifier = modifier.clickable {
                        navigateToDetail(data.crochet.id)
                    }
                )
            }
        }
    }
}